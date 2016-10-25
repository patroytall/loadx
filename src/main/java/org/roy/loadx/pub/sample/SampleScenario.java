package org.roy.loadx.pub.sample;

import org.roy.loadx.pub.api.ExecutionData;
import org.roy.loadx.pub.api.JobInitializer;
import org.roy.loadx.pub.api.Scenario;
import org.roy.loadx.pub.api.ScenarioClassInitializer;
import org.roy.loadx.pub.api.TransactionRecorder;

public class SampleScenario implements Scenario {
  public enum Data {
    SCENARIO_TYPE
  };

  public enum ScenarioType {
    TYPE1, TYPE2
  }

  private TransactionRecorder transactionRecorder;
  private ScenarioType scenarioType;
  private long runCount = 0;

  @Override
  public void initializeScenarioThread(ExecutionData scenarioData,
      ExecutionData scenarioClassData, ExecutionData jobData,
      ScenarioClassInitializer scenarioClassInitializer, JobInitializer jobInitializer,
      TransactionRecorder transactionRecorder) {
    this.transactionRecorder = transactionRecorder;

    scenarioType = ((ScenarioType) scenarioData.getObject(Data.SCENARIO_TYPE));
    println("initialize scenario thread - scenario data - scenario type: "
        + scenarioType.toString().toLowerCase());

    println("initialize scenario thread - scenario class data - url: "
        + scenarioClassData.getString(SampleScenarioClassInitializer.Data.SCENARIO_CLASS));
    println("initialize scenario thread - job data - job: "
        + jobData.getString(SampleJobInitializer.Data.JOB));

    println("initialize scenario thread - scenario class initializer - simple name "
        + ((SampleScenarioClassInitializer) scenarioClassInitializer).getSimpleName());
    println("initialize scenario thread - job initializer - simple name "
        + ((SampleJobInitializer) jobInitializer).getSimpleName());
  }

  @Override
  public void start() {
    action("start", false);
  }

  @Override
  public void run() {
    runCount++;
    boolean fail = scenarioType == ScenarioType.TYPE2 && runCount % 2 == 0;
    action("run", fail);
  }

  @Override
  public void end() {
    action("end", false);
  }

  private void action(String name, boolean fail) {
    String transactionName = name + "-" + scenarioType.toString().toLowerCase();
    transactionRecorder.start(transactionName);
    println(transactionName);
    sleep();
    if (fail) {
      throw new RuntimeException("sample transaction failure");
    }
    transactionRecorder.end();
  }

  private void sleep() {
    try {
      Thread.sleep((long) ((Math.random() * 1000) + 1));
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void terminateScenarioThread() {
    println("terminate scenario thread - scenario type: " + scenarioType);
  }

  private static void println(String str) {
    System.out.println(str);
    System.out.flush();
  }
}