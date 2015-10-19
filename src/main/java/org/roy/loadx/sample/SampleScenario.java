package org.roy.loadx.sample;

import org.roy.loadx.api.Scenario;

public class SampleScenario implements Scenario {

	@Override
	public void initialize() {
		System.out.println("initialize");

	}

	@Override
	public void run() {
		System.out.println("run");
	}

	@Override
	public void terminate() {
		System.out.println("terminate");
	}

}
