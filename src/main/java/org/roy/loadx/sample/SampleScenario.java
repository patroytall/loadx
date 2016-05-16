package org.roy.loadx.sample;

import org.roy.loadx.api.ExecutionData;
import org.roy.loadx.api.Scenario;
import org.roy.loadx.api.TransactionRecorder;

public class SampleScenario implements Scenario {
  public enum Data {
    URL, SCENARIO_TYPE
  };

  public enum ScenarioType {
    GREAT, BETTER
  }

  private TransactionRecorder transactionRecorder;
  private String scenarioType;

  @Override
  public void initializeObject(ExecutionData jobScenarioClassData,
      ExecutionData jobScenarioObjectData, TransactionRecorder transactionRecorder) {
    this.transactionRecorder = transactionRecorder;
    System.out.println("initialize - scenario class data - url: "
        + jobScenarioClassData.getString(Data.URL.toString()));
    scenarioType =
        ((String) jobScenarioObjectData.getObject(Data.SCENARIO_TYPE.toString())).toLowerCase();
    System.out.println("initialize - scenario object data - scenario type: " + scenarioType);
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
    System.out.println(transactionName);
    transactionRecorder.end();
  }

  @Override
  public void terminateObject() {
    System.out.println("terminate object - " + scenarioType);
  }
}