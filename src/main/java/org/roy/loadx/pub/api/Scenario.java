package org.roy.loadx.pub.api;

public interface Scenario {
  void initializeScenarioThread(ExecutionData scenarioData, ExecutionData scenarioClassData,
      ExecutionData jobData, ScenarioClassInitializer scenarioClassInitializer,
      JobInitializer jobInitializer, TransactionRecorder transactionRecorder);

  void run();

  void end();

  void terminateScenarioThread();

  void start();
}