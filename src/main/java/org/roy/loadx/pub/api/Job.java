package org.roy.loadx.pub.api;

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

  /**
   * Minimum amount of time the job will run. start/run/end/scenario iterations will complete before
   * the time is checked and the scenarios stop. Should generally be used with scenario iteration
   * count at default value.
   */
  void setExecutionTime(long seconds);

  void setJobInitializer(JobInitializer jobInitializer);

  void setScenarioClassInitializer(Object scenarioClass,
      ScenarioClassInitializer scenarioClassInitializer);

  long getDefaultScenarioIterationCount();

  long getDefaultScenarioRunIterationCount();

  int getDefaultScenarioThreadCount();

  /**
   * @return 0 if not set
   */
  long getJobExecutionTimeSeconds();

  ExecutionData getJobData();

  ExecutionData getScenarioClassData(Object scenarioClass);
}