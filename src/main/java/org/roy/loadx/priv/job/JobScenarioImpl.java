package org.roy.loadx.priv.job;

import org.roy.loadx.pub.api.ExecutionData;
import org.roy.loadx.pub.api.JobScenario;
import org.roy.loadx.pub.api.Scenario;

public class JobScenarioImpl implements JobScenario {
  private final ExecutionData scenarioData = new ExecutionDataImpl();
  private final Class<Scenario> scenarioClass;

  JobScenarioImpl(Class<Scenario> scenarioClass) {
    this.scenarioClass = scenarioClass;
  }

  @Override
  public ExecutionData getScenarioData() {
    return scenarioData;
  }
  
  public Class<Scenario> getScenarioClass() {
    return scenarioClass;
  }
}