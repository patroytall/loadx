package org.roy.loadx.invocation;

import org.roy.loadx.SpringConfig;
import org.roy.loadx.api.ExecutionData;
import org.roy.loadx.api.Job;
import org.roy.loadx.api.JobInitializer;
import org.roy.loadx.api.Scenario;
import org.roy.loadx.job.JobImpl;
import org.roy.loadx.job.ScenarioRunner;
import org.roy.loadx.transaction.TimeProvider;
import org.roy.loadx.transaction.TransactionAggregator;
import org.roy.loadx.transaction.TransactionPrintRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/*
 * To use Eclipse TCP/IP Monitor: 
 * - configure local hosts file with entry like: 127.0.0.1 localhost.remotedomain.com 
 * - use host localhost.remotedomain.com:1234 as balancer URL in LoadX 
 * - configure monitor to listen on port 1234 and forward to host.remotedomain.com
 */
@Component
public class LoadX {
  @Autowired
  private TimeProvider timeProvider;

  @Autowired
  private TransactionPrintRunner transactionPrintRunner;

  @Autowired
  private ApplicationContext applicationContext;
  
  private static LoadX instance;
  
  @PostConstruct
  public void registerInstance() {
      instance = this;
  }
  
  public static void main(String[] args) {
    try (AnnotationConfigApplicationContext ac =
        new AnnotationConfigApplicationContext(SpringConfig.class)) {
      LoadX loadX = ac.getBean(LoadX.class);
      loadX.run(args);
    }
  }

  public void run(String[] args) {
    new Thread(transactionPrintRunner).start();
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

  private Scenario getScenario(Job job) {
    try {
      return (Scenario) job.getScenarioClass().newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  private void runJob(JobImpl jobImpl) {
    runJobIntialize(jobImpl);

    ExecutorService executorService =
        Executors.newFixedThreadPool(jobImpl.getDefaultScenarioUserCount());

    TransactionAggregator transactionAggregator = new TransactionAggregator();
    transactionPrintRunner.print(transactionAggregator);

    for (int i = 0; i < jobImpl.getDefaultScenarioUserCount(); ++i) {
      ExecutionData scenarioClassData = jobImpl.getScenarioClassData(jobImpl.getScenarioClass());
      executorService.execute(new ScenarioRunner(getScenario(jobImpl),
          jobImpl.getDefaultScenarioIterationCount(), jobImpl.getDefaultScenarioRunIterationCount(),
          scenarioClassData, transactionAggregator, timeProvider));
    }

    executorService.shutdown();

    try {
      executorService.awaitTermination(1, TimeUnit.DAYS);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    runJobTerminate(jobImpl);

    transactionPrintRunner.end();
  }
  
  public static ApplicationContext getApplicationContext() {
    return instance.applicationContext;
  }
}