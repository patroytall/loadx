package org.roy.loadx.api;

public interface ScenarioClassInitializer {
  void initializeScenarioClass(ExecutionData scenarioClassData, ExecutionData jobData);
  void terminateScenarioClass(ExecutionData scenarioClassData, ExecutionData jobData);
}