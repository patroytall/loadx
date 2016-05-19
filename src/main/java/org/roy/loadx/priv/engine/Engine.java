package org.roy.loadx.priv.engine;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.roy.loadx.priv.job.JobImpl;
import org.roy.loadx.priv.job.JobScenarioImpl;
import org.roy.loadx.priv.job.ScenarioRunner;
import org.roy.loadx.priv.transaction.TransactionAggregator;
import org.roy.loadx.priv.transaction.print.TransactionPrintRunner;
import org.roy.loadx.priv.transaction.print.TransactionPrintRunnerThread;
import org.roy.loadx.priv.transaction.print.TransactionPrinterFactory;
import org.roy.loadx.priv.transaction.print.TransactionPrinterFactoryImpl;
import org.roy.loadx.pub.api.ExecutionData;
import org.roy.loadx.pub.api.JobInitializer;
import org.roy.loadx.pub.api.Scenario;
import org.roy.loadx.pub.api.ScenarioClassInitializer;
import org.roy.loadx.pub.invocation.LoadX;

import com.beust.jcommander.JCommander;
import com.google.common.collect.FluentIterable;

public class Engine {
  private Iterator<JobScenarioImpl> infiniteJobScenarioIterator;

  private Configuration configuration;
  private TransactionAggregator transactionAggregator = new TransactionAggregator();
  private TransactionPrintRunner transactionPrintRunner;

  public void run(String[] args) {
    run(args, new EngineConfiguration());
  }

  public void run(String[] args, Configuration configuration) {
    this.configuration = configuration;
    ArgumentParser argumentParser = new ArgumentParser();
    new JCommander(argumentParser, args);

    WebServer webServer = null;
    if (argumentParser.getWeb()) {
      webServer = new WebServer();
    }
    runJavaScript(args[0]);

    if (webServer != null) {
      webServer.stop();
    }
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
      ExecutorService executorService, TransactionAggregator transactionAggregator) {
    ScenarioClassInitializer scenarioClassInitializer =
        jobImpl.getScenarioClassInitializers().get(jobScenarioImpl.getScenarioClass());
    executorService.execute(
        new ScenarioRunner(getScenario(jobScenarioImpl), jobImpl.getDefaultScenarioIterationCount(),
            jobImpl.getDefaultScenarioRunIterationCount(), jobScenarioImpl.getScenarioData(),
            jobImpl.getScenarioClassData(jobScenarioImpl.getScenarioClass()), jobImpl.getJobData(),
            scenarioClassInitializer, jobImpl.getJobInitializer(), transactionAggregator,
            configuration.getTimeProvider()));
  }

  private void startTransactionPrintRunner() {
    transactionPrintRunner = configuration.getTransactionPrintRunner();
    if (transactionPrintRunner == null) {
      TransactionPrinterFactory transactionPrinterFactory = new TransactionPrinterFactoryImpl();
      transactionPrintRunner = new TransactionPrintRunnerThread(
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

  private void runJobScenarios(JobImpl jobImpl) {
    ExecutorService executorService =
        Executors.newFixedThreadPool(jobImpl.getDefaultScenarioThreadCount());
    infiniteJobScenarioIterator = FluentIterable.from(jobImpl.getJobScenarios()).cycle().iterator();
    for (int i = 0; i < jobImpl.getDefaultScenarioThreadCount(); ++i) {
      runJobScenario(infiniteJobScenarioIterator.next(), jobImpl, executorService,
          transactionAggregator);
    }

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
  }
}
