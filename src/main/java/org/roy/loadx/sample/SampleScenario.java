package org.roy.loadx.sample;

import org.roy.loadx.api.Scenario;

public class SampleScenario implements Scenario {

	@Override
	public void start() {
		System.out.println("start");

	}

	@Override
	public void stop() {
		System.out.println("stop");
	}

}
