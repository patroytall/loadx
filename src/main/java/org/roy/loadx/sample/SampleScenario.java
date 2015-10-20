package org.roy.loadx.sample;

import org.roy.loadx.api.Scenario;

public class SampleScenario implements Scenario {

	@Override
	public void initializeUser() {
		System.out.println("initialize user");
	}

	@Override
	public void start() {
		System.out.println("start");

	}

	@Override
	public void run() {
		System.out.println("run");
	}

	@Override
	public void end() {
		System.out.println("end");
	}

	@Override
	public void terminateUser() {
		System.out.println("terminate user");
	}
}
