package org.roy.loadx.job;

import org.roy.loadx.api.Job;
import org.roy.loadx.api.Scenario;

public class JobImpl implements Job {
	private Scenario scenario;

	@Override
	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}

	@Override
	public Scenario getScenario() {
		return scenario;
	}
}
