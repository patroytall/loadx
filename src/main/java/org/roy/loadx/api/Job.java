package org.roy.loadx.api;

public interface Job {
	static final long DEFAULT_SCENARIO_ITERATION_COUNT = 1;
	static final long DEFAULT_SCENARIO_RUN_ITERATION_COUNT = 1;
	static final int DEFAULT_SCENARIO_USER_COUNT = 1;

	Object addScenario(Object scenarioClass);

	/**
	 * Override the DEFAULT_SCENARIO_ITERATION_COUNT value
	 **/
	void setDefaultScenarioIterationCount(long count);

	/**
	 * Override the DEFAULT_SCENARIO_RUN_ITERATION_COUNT value
	 */
	void setDefaultScenarioRunIterationCount(int count);

	/**
	 * Override the DEFAULT_SCENARIO_USER_COUNT value
	 */
	void setDefaultScenarioUserCount(int count);

	void setJobInitializer(JobInitializer jobInitializer);

	Class<Scenario> getScenarioClass();

	long getDefaultScenarioIterationCount();

	long getDefaultScenarioRunIterationCount();

	int getDefaultScenarioUserCount();

	ExecutionData getScenarioClassData(Object scenarioClass);
}