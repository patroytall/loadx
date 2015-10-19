package org.roy.loadx.job;

import org.roy.loadx.api.Scenario;

public class ScenarioRunner implements Runnable {
	private final Scenario scenario;
	private final long scenarioIterationCount;

	public ScenarioRunner(Scenario scenario, long scenarioIterationCount) {
		this.scenario = scenario;
		this.scenarioIterationCount = scenarioIterationCount;
	}

	@Override
	public void run() {
		scenario.initialize();
		for (long i = 0; i < scenarioIterationCount; ++i) {
			scenario.run();
		}
		scenario.terminate();
	}
}
