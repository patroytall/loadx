package org.roy.loadx.api;

public interface Scenario {
  void initializeObject(ExecutionData jobScenarioClassData, ExecutionData jobScenarioObjectData,
      TransactionRecorder transactionRecorder);

  void start();

  void run();

  void end();

  void terminateObject();
}
