package org.roy.loadx.job;

import org.roy.loadx.api.Scenario;
import org.roy.loadx.api.ExecutionData;

public class ScenarioRunner implements Runnable {
	private final Scenario scenario;
	private final long scenarioIterationCount;
	private final long scenarioRunIterationCount;
	private final ExecutionData scenarioClassData;

	public ScenarioRunner(Scenario scenario, long defaultScenarioIterationCount, long defaultScenarioRunIterationCount,
			ExecutionData scenarioClassData) {
		this.scenario = scenario;
		this.scenarioIterationCount = defaultScenarioIterationCount;
		this.scenarioRunIterationCount = defaultScenarioRunIterationCount;
		this.scenarioClassData = scenarioClassData;
	}

	@Override
	public void run() {
		scenario.initializeObject(scenarioClassData);
		for (long i = 0; i < scenarioIterationCount; ++i) {
			scenario.start();
			for (long j = 0; j < scenarioRunIterationCount; ++j) {
				scenario.run();
			}
			scenario.end();
		}
		scenario.terminateObject();
	}
}
