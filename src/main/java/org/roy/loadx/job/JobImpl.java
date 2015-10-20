package org.roy.loadx.job;

import java.util.HashMap;
import java.util.Map;

import org.roy.loadx.api.Job;
import org.roy.loadx.api.JobInitializer;
import org.roy.loadx.api.Scenario;
import org.roy.loadx.api.ScenarioData;

public class JobImpl implements Job {
	private final Map<Object, ScenarioData> scenarioClassDataMap = new HashMap<>();

	private Scenario scenario;
	private long scenarioIterationCount = DEFAULT_SCENARIO_ITERATION_COUNT;
	private int scenarioUserCount = DEFAULT_SCENARIO_USER_COUNT;
	private JobInitializer jobInitializer;

	@Override
	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}

	@Override
	public Scenario getScenario() {
		return scenario;
	}

	@Override
	public void setScenarioIterationCount(long count) {
		scenarioIterationCount = count;
	}

	@Override
	public long getScenarioIterationCount() {
		return scenarioIterationCount;
	}

	@Override
	public void setScenarioUserCount(int count) {
		scenarioUserCount = count;
	}

	@Override
	public int getScenarioUserCount() {
		return scenarioUserCount;
	}

	@Override
	public ScenarioData getScenarioClassData(Object scenario) {
		String className = scenario.getClass().getName();
		ScenarioData scenarioData = scenarioClassDataMap.get(className);
		if (scenarioData == null) {
			scenarioData = new ScenarioDataImpl();
			scenarioClassDataMap.put(className, scenarioData);
		}
		return scenarioData;
	}

	@Override
	public void setJobInitializer(JobInitializer jobInitializer) {
		this.jobInitializer = jobInitializer;
	}

	public JobInitializer getJobInitializer() {
		return jobInitializer;
	}
}
