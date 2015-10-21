package org.roy.loadx.sample;

import org.roy.loadx.api.ExecutionData;
import org.roy.loadx.api.Scenario;
import org.roy.loadx.api.TransactionRecorder;

public class SampleScenario implements Scenario {
	public enum Data {
		URL
	};

	@Override
	public void initializeObject(ExecutionData scenarioClassData, TransactionRecorder transactionRecorder) {
		System.out.println("initialize user - url: " + scenarioClassData.getString(Data.URL.toString()));
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
	public void terminateObject() {
		System.out.println("terminate user");
	}
}