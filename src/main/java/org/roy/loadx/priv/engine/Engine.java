package org.roy.loadx.priv.engine;

import com.google.common.collect.FluentIterable;

import org.roy.loadx.priv.engine.time.TimeHandler;
import org.roy.loadx.priv.job.JobImpl;
import org.roy.loadx.priv.job.JobScenarioImpl;
import org.roy.loadx.priv.job.ScenarioRunner;
import org.roy.loadx.priv.transaction.TransactionAggregatorImpl;
import org.roy.loadx.priv.transaction.TransactionListener;
import org.roy.loadx.priv.transaction.grapher.TransactionGrapher;
import org.roy.loadx.priv.transaction.print.TransactionPrintRunner;
import org.roy.loadx.priv.transaction.print.TransactionPrintRunnerThread;
import org.roy.loadx.priv.transaction.print.TransactionPrinterFactory;
import org.roy.loadx.priv.transaction.print.TransactionPrinterFactoryImpl;
import org.roy.loadx.priv.transaction.writer.TransactionWriter;
import org.roy.loadx.priv.transaction.writer.TransactionWriterStub;
import org.roy.loadx.pub.api.ExecutionData;
import org.roy.loadx.pub.api.JobInitializer;
import org.roy.loadx.pub.api.Scenario;
import org.roy.loadx.pub.api.ScenarioClassInitializer;
import org.roy.loadx.pub.invocation.LoadX;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Engine {
  private final List<ScenarioRunner> scenarioRunners = new ArrayList<>();

  private Iterator<JobScenarioImpl> infiniteJobScenarioIterator;
  private Configuration configuration;
  private TransactionAggregatorImpl transactionAggregator = new TransactionAggregatorImpl();
  private TransactionWriter transactionWriter = new TransactionWriterStub();
  private TransactionGrapher transactionGrapher = new TransactionGrapher();
  private TransactionPrintRunner transactionPrintRunner;

  public void run(String[] args) {
    run(args, new EngineConfiguration());
  }

  public void run(String[] args, Configuration configuration) {
    this.configuration = configuration;
    parseArgumentsAndRun(args);
  }

  private void parseArgumentsAndRun(String[] args) {
    ArgumentsParser argumentParser = new ArgumentsParser(args);

    WebServer webServer = null;
    if (argumentParser.getWebServer() || Properties.getWebserver()) {
      webServer = new WebServer(transactionGrapher);
    }

    if (checkArguments(args)) {
      runJavaScript(args[0]);

      if (webServer != null) {
        webServer.stop();
      }
    }
  }

  private boolean checkArguments(String[] args) {
    boolean valid = true;
    if (args.length == 0 || args.length > 1) {
      System.out.println("ERROR - invalid number of arguments: " + args.length);
      valid = false;
    }
    if (!valid) {
      System.out.println("usage: LoadX <JavaScriptJob>");
      System.out.println("       where JavaScriptJob is a JavaScript file under resources");
    }
    return valid;
  }

  private void runJavaScript(String resourcePath) {
    ScriptEngineManager factory = new ScriptEngineManager();
    ScriptEngine engine = factory.getEngineByName("nashorn");

    String script;
    try {
      URL url = LoadX.class.getResource("/" + resourcePath);
      if (url == null) {
        throw new RuntimeException("invalid resource path: " + resourcePath);
      }
      script = new String(Files.readAllBytes(Paths.get(url.toURI())));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    JobImpl job = new JobImpl();

    try {
      Object global = engine.eval("this");
      Object jsObject = engine.eval("Object");

      Invocable invocable = (Invocable) engine;

      invocable.invokeMethod(jsObject, "bindProperties", global, job);

      engine.eval(script);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    runJob(job);
  }

  private void invokeJobIntialize(JobImpl jobImpl) {
    JobInitializer jobInitializer = jobImpl.getJobInitializer();
    if (jobInitializer != null) {
      jobInitializer.initializeJob(jobImpl.getJobData());
    }
  }

  private void runJobTerminate(JobImpl jobImpl) {
    JobInitializer jobInitializer = jobImpl.getJobInitializer();
    if (jobInitializer != null) {
      jobInitializer.terminateJob();
    }
  }

  private Scenario getScenario(JobScenarioImpl jobScenarioImpl) {
    try {
      return (Scenario) jobScenarioImpl.getScenarioClass().newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  private void runJobScenario(JobScenarioImpl jobScenarioImpl, JobImpl jobImpl,
      ExecutorService executorService, TransactionAggregatorImpl transactionAggregator) {
    ScenarioClassInitializer scenarioClassInitializer =
        jobImpl.getScenarioClassInitializers().get(jobScenarioImpl.getScenarioClass());
    List<TransactionListener> transactionListeners =
        Arrays.asList(transactionAggregator, transactionWriter, transactionGrapher);
    TimeHandler timeHandler = new TimeHandler(configuration.getTimeProvider());
    boolean waitForExecutionTimeDone = jobImpl.getJobExecutionTimeSeconds() > 0;
    ScenarioRunner scenarioRunner =
        new ScenarioRunner(getScenario(jobScenarioImpl),
            jobImpl.getDefaultScenarioIterationCount(),
            jobImpl.getDefaultScenarioRunIterationCount(), jobScenarioImpl.getScenarioData(),
            jobImpl.getScenarioClassData(jobScenarioImpl.getScenarioClass()),
            jobImpl.getJobData(), scenarioClassInitializer, jobImpl.getJobInitializer(),
            transactionListeners, timeHandler, waitForExecutionTimeDone);
    scenarioRunners.add(scenarioRunner);
    executorService.execute(scenarioRunner);
  }

  private void startTransactionPrintRunner() {
    transactionPrintRunner = configuration.getTransactionPrintRunner();
    if (transactionPrintRunner == null) {
      TransactionPrinterFactory transactionPrinterFactory = new TransactionPrinterFactoryImpl();
      transactionPrintRunner =
          new TransactionPrintRunnerThread(
              transactionPrinterFactory.getInstance(transactionAggregator));
    }
    transactionPrintRunner.start();
  }

  private void invokeScenarioClassInitializerInitializes(JobImpl jobImpl) {
    for (Map.Entry<Class<Scenario>, ScenarioClassInitializer> entry : jobImpl
        .getScenarioClassInitializers().entrySet()) {
      ExecutionData scenarioClassData = jobImpl.getScenarioClassData(entry.getKey());
      entry.getValue().initializeScenarioClass(entry.getValue().getClass(), scenarioClassData,
          jobImpl.getJobData());
    }
  }

  private void invokeScenarioClassInitializerTerminates(JobImpl jobImpl) {
    for (Map.Entry<Class<Scenario>, ScenarioClassInitializer> entry : jobImpl
        .getScenarioClassInitializers().entrySet()) {
      entry.getValue().terminateScenarioClass();
    }
  }

  private void executionTimeWait(JobImpl jobImpl) {
    try {
      Thread.sleep(jobImpl.getJobExecutionTimeSeconds() * 1000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    for (ScenarioRunner scenarioRunner : scenarioRunners) {
      scenarioRunner.setExecutionTimeDone();
    }
  }

  private void runJobScenarios(JobImpl jobImpl) {
    ExecutorService executorService =
        Executors.newFixedThreadPool(jobImpl.getDefaultScenarioThreadCount());
    infiniteJobScenarioIterator =
        FluentIterable.from(jobImpl.getJobScenarios()).cycle().iterator();
    for (int i = 0; i < jobImpl.getDefaultScenarioThreadCount(); ++i) {
      runJobScenario(infiniteJobScenarioIterator.next(), jobImpl, executorService,
          transactionAggregator);
    }

    executionTimeWait(jobImpl);

    executorService.shutdown();

    try {
      executorService.awaitTermination(1, TimeUnit.DAYS);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private void runJob(JobImpl jobImpl) {
    invokeJobIntialize(jobImpl);

    invokeScenarioClassInitializerInitializes(jobImpl);

    startTransactionPrintRunner();

    runJobScenarios(jobImpl);

    invokeScenarioClassInitializerTerminates(jobImpl);

    runJobTerminate(jobImpl);

    transactionPrintRunner.done();

    transactionWriter.terminate();
  }
}
