package org.roy.loadx.pub.api;

public interface ScenarioClassInitializer {
  void initializeScenarioClass(Class<? extends ScenarioClassInitializer> scenarioClass,
      ExecutionData scenarioClassData, ExecutionData jobData);

  void terminateScenarioClass();
}