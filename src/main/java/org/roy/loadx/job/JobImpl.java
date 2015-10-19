package org.roy.loadx.job;

import org.roy.loadx.api.Job;
import org.roy.loadx.api.Scenario;

public class JobImpl implements Job {
	private Scenario scenario;
	private long scenarioIterationCount = DEFAULT_SCENARIO_ITERATION_COUNT;
	private int scenarioUserCount = DEFAULT_SCENARIO_USER_COUNT;

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
}
