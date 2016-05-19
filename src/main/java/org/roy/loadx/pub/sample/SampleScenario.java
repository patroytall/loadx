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
    T1, T2
  }

  private TransactionRecorder transactionRecorder;
  private String scenarioType;

  @Override
  public void initializeScenarioThread(ExecutionData scenarioData, ExecutionData scenarioClassData,
      ExecutionData jobData, ScenarioClassInitializer scenarioClassInitializer,
      JobInitializer jobInitializer, TransactionRecorder transactionRecorder) {
    this.transactionRecorder = transactionRecorder;

    scenarioType =
        ((ScenarioType) scenarioData.getObject(Data.SCENARIO_TYPE)).toString().toLowerCase();
    println("initialize scenario thread - scenario data - scenario type: " + scenarioType);

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
    action("start");
  }

  @Override
  public void run() {
    action("run");
  }

  @Override
  public void end() {
    action("end");
  }

  private void action(String name) {
    String transactionName = name + " - " + scenarioType;
    transactionRecorder.start(transactionName);
    println(transactionName);
    transactionRecorder.end();
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