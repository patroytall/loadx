package org.roy.loadx.api;

public interface ScenarioClassInitializer {
  void initializeScenarioClass(Class<? extends ScenarioClassInitializer> scenarioClass,
      ExecutionData scenarioClassData, ExecutionData jobData);

  void terminateScenarioClass();
}