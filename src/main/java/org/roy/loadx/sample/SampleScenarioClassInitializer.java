package org.roy.loadx.sample;

import org.roy.loadx.api.ExecutionData;
import org.roy.loadx.api.ScenarioClassInitializer;

public class SampleScenarioClassInitializer implements ScenarioClassInitializer {
  public enum Data {
    SCENARIO_CLASS
  }

  @Override
  public void initializeScenarioClass(Class<? extends ScenarioClassInitializer> scenarioClass,
      ExecutionData scenarioClassData, ExecutionData jobData) {
    println("initialize scenario class - scenario class - simple name - "
        + scenarioClass.getSimpleName());
    println("initialize scenario class - scenario class data - scenario class - "
        + scenarioClassData.getString(Data.SCENARIO_CLASS));
    println("initialize scenario class - job data - job - "
        + jobData.getString(SampleJobInitializer.Data.JOB));
  }

  @Override
  public void terminateScenarioClass() {
    println("terminate scenario class");
  }

  public String getSimpleName() {
    return getClass().getSimpleName();
  }

  private static void println(String str) {
    System.out.println(str);
    System.out.flush();
  }
}
