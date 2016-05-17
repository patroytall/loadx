package org.roy.loadx.api;

public interface Scenario {
  void initializeScenarioThread(ExecutionData scenarioData, ExecutionData scenarioClassData,
      ExecutionData jobData, TransactionRecorder transactionRecorder);

  void run();

  void end();

  void terminateScenarioThread(ExecutionData scenarioData, ExecutionData scenarioClassData,
      ExecutionData jobData);

  void start();
}