package org.roy.loadx.api;

public interface Job {
	static final long DEFAULT_SCENARIO_ITERATION_COUNT = 1;
	static final int DEFAULT_SCENARIO_USER_COUNT = 1;

	void setScenario(Scenario scenario);

	void setScenarioIterationCount(long count);

	void setScenarioUserCount(int count);

	void setJobInitializer(JobInitializer jobInitializer);

	Scenario getScenario();

	long getScenarioIterationCount();

	int getScenarioUserCount();

	ScenarioData getScenarioClassData(Object scenario);
}
