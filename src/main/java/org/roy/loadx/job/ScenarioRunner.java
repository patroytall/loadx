package org.roy.loadx.job;

import org.roy.loadx.api.Scenario;

public class ScenarioRunner implements Runnable {
	private final Scenario scenario;
	private final long scenarioIterationCount;
	private final long scenarioRunIterationCount;

	public ScenarioRunner(Scenario scenario, long defaultScenarioIterationCount, long defaultScenarioRunIterationCount) {
		this.scenario = scenario;
		this.scenarioIterationCount = defaultScenarioIterationCount;
		this.scenarioRunIterationCount = defaultScenarioRunIterationCount;
	}

	@Override
	public void run() {
		scenario.initializeUser();
		for (long i = 0; i < scenarioIterationCount; ++i) {
			scenario.start();
			for (long j = 0; j < scenarioRunIterationCount; ++j) {
				scenario.run();
			}
			scenario.end();
		}
		scenario.terminateUser();
	}
}
