package org.roy.loadx.invocation;

import com.google.common.collect.FluentIterable;

import org.roy.loadx.Configuration;
import org.roy.loadx.EngineConfiguration;
import org.roy.loadx.api.JobInitializer;
import org.roy.loadx.api.Scenario;
import org.roy.loadx.job.JobImpl;
import org.roy.loadx.job.JobScenarioImpl;
import org.roy.loadx.job.ScenarioRunner;
import org.roy.loadx.transaction.TransactionAggregator;
import org.roy.loadx.transaction.TransactionPrinterFactoryImpl;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/*
 * To use Eclipse TCP/IP Monitor: 
 * - configure local hosts file with entry like: 127.0.0.1 localhost.remotedomain.com 
 * - use host localhost.remotedomain.com:1234 as balancer URL in LoadX 
 * - configure monitor to listen on port 1234 and forward to host.remotedomain.com
 */
public class LoadX {
  private Iterator<JobScenarioImpl> infiniteJobScenarioIterator;

  private Configuration configuration;

  public static void main(String[] args) {
    new LoadX().run(args);
  }

  public void run(String[] args) {
    run(args, new EngineConfiguration());
  }

  public void run(String[] args, Configuration configuration) {
    this.configuration = configuration;
    new Thread(this.configuration.getTransactionPrintRunner()).start();
    runJavaScript(args[0]);
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

  private void runJobIntialize(JobImpl jobImpl) {
    JobInitializer jobInitializer = jobImpl.getJobInitializer();
    if (jobInitializer != null) {
      jobInitializer.initialize();
    }
  }

  private void runJobTerminate(JobImpl jobImpl) {
    JobInitializer jobInitializer = jobImpl.getJobInitializer();
    if (jobInitializer != null) {
      jobInitializer.terminate();
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
    executorService.execute(new ScenarioRunner(getScenario(jobScenarioImpl),
        jobImpl.getDefaultScenarioIterationCount(), jobImpl.getDefaultScenarioRunIterationCount(),
        jobImpl.getScenarioClassData(jobScenarioImpl.getScenarioClass()), jobScenarioImpl.getObjectData(), transactionAggregator,
        configuration.getTimeProvider()));
  }

  private void runJob(JobImpl jobImpl) {
    runJobIntialize(jobImpl);

    ExecutorService executorService =
        Executors.newFixedThreadPool(jobImpl.getDefaultScenarioUserCount());

    TransactionAggregator transactionAggregator = new TransactionAggregator();
    configuration.setTransactionPrintRunner(new TransactionPrinterFactoryImpl(), transactionAggregator);;

    infiniteJobScenarioIterator = FluentIterable.from(jobImpl.getJobScenarios()).cycle().iterator();
    for (int i = 0; i < jobImpl.getDefaultScenarioUserCount(); ++i) {
      runJobScenario(infiniteJobScenarioIterator.next(), jobImpl, executorService, transactionAggregator);
    }

    executorService.shutdown();

    try {
      executorService.awaitTermination(1, TimeUnit.DAYS);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    runJobTerminate(jobImpl);

    configuration.getTransactionPrintRunner().stop();
  }
}