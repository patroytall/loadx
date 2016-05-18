package org.roy.loadx.api;

public interface Job {
  static final long DEFAULT_SCENARIO_ITERATION_COUNT = 1;
  static final long DEFAULT_SCENARIO_RUN_ITERATION_COUNT = 1;
  static final int DEFAULT_SCENARIO_THREAD_COUNT = 1;

  Object addScenario(Object scenarioClass);

  /**
   * Override the DEFAULT_SCENARIO_ITERATION_COUNT value
   **/
  void setDefaultScenarioIterationCount(long count);

  /**
   * Override the DEFAULT_SCENARIO_RUN_ITERATION_COUNT value
   */
  void setDefaultScenarioRunIterationCount(long count);

  /**
   * Override the DEFAULT_SCENARIO_THREAD_COUNT value
   */
  void setDefaultScenarioThreadCount(int count);

  void setJobInitializer(JobInitializer jobInitializer);

  void setScenarioClassInitializer(Object scenarioClass,
      ScenarioClassInitializer scenarioClassInitializer);

  long getDefaultScenarioIterationCount();

  long getDefaultScenarioRunIterationCount();

  int getDefaultScenarioThreadCount();

  ExecutionData getJobData();

  ExecutionData getScenarioClassData(Object scenarioClass);
}