package org.roy.loadx.sample;

import org.roy.loadx.api.ExecutionData;
import org.roy.loadx.api.Scenario;
import org.roy.loadx.api.ScenarioClassInitializer;
import org.roy.loadx.api.TransactionRecorder;

public class SampleScenario implements Scenario, ScenarioClassInitializer {
  public enum Data {
    URL, SCENARIO_TYPE
  };

  public enum ScenarioType {
    GREAT, BETTER
  }

  private TransactionRecorder transactionRecorder;
  private String scenarioType;

  @Override
  public void initializeScenarioThread(ExecutionData scenarioData, ExecutionData scenarioClassData,
      ExecutionData jobData, TransactionRecorder transactionRecorder) {
    this.transactionRecorder = transactionRecorder;
    scenarioType =
        ((ScenarioType) scenarioData.getObject(Data.SCENARIO_TYPE)).toString().toLowerCase();
    println("initialize scenario thread - scenario data - scenario type: " + scenarioType);
    println("initialize scenario thread - scenario class data - url: "
        + scenarioClassData.getString(Data.URL));
    println("initialize scenario thread - job data - project: "
        + jobData.getString(SampleJobInitializer.Data.PROJECT));
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
  public void terminateScenarioThread(ExecutionData scenarioData, ExecutionData scenarioClassData,
      ExecutionData jobData) {
    println("terminate scenario thread - scenario type: " + scenarioType);
    println("terminate scenario thread - scenario data - scenario type: "
        + ((ScenarioType) scenarioData.getObject(Data.SCENARIO_TYPE)).toString().toLowerCase());
    println("terminate scenario thread - scenario class data - url: "
        + scenarioClassData.getString(Data.URL));
    println("terminate scenario thread - job data - project: "
        + jobData.getString(SampleJobInitializer.Data.PROJECT));
  }

  private static void println(String str) {
    System.out.println(str);
    System.out.flush();
  }

  @Override
  public void initializeScenarioClass(ExecutionData scenarioClassData, ExecutionData jobData) {
    // TODO Auto-generated method stub
  }

  @Override
  public void terminateScenarioClass(ExecutionData scenarioClassData, ExecutionData jobData) {
    // TODO Auto-generated method stub
  }
}