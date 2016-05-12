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

  @Override
  public void initializeObject(ExecutionData jobScenarioClassData,
      ExecutionData jobScenarioObjectData, TransactionRecorder transactionRecorder) {
    this.transactionRecorder = transactionRecorder;
    System.out.println("initialize - scenario class data - url: "
        + jobScenarioClassData.getString(Data.URL.toString()));
    System.out.println("initialize - scenario object data - url: "
        + jobScenarioObjectData.getObject(Data.SCENARIO_TYPE.toString()));
  }

  @Override
  public void start() {
    transactionRecorder.start("start");
    System.out.println("start");
    transactionRecorder.end();
  }

  @Override
  public void run() {
    transactionRecorder.start("run");
    System.out.println("run");
    transactionRecorder.end();
  }

  @Override
  public void end() {
    transactionRecorder.start("end");
    System.out.println("end");
    transactionRecorder.end();
  }

  @Override
  public void terminateObject() {
    System.out.println("terminate user");
  }
}