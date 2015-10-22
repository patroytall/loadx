package org.roy.loadx.sample;

import org.roy.loadx.api.ExecutionData;
import org.roy.loadx.api.Scenario;
import org.roy.loadx.api.TransactionRecorder;

public class SampleScenario implements Scenario {
	public enum Data {
		URL
	};

	private TransactionRecorder transactionRecorder;

	@Override
	public void initializeObject(ExecutionData scenarioClassData, TransactionRecorder transactionRecorder) {
		this.transactionRecorder = transactionRecorder;
		System.out.println("initialize user - url: " + scenarioClassData.getString(Data.URL.toString()));
	}

	@Override
	public void start() {
		transactionRecorder.start("start");
		System.out.println("start");
		transactionRecorder.end();
	}

	@Override
	public void run() {
		transactionRecorder.start("start");
		System.out.println("run");
		transactionRecorder.end();
	}

	@Override
	public void end() {
		transactionRecorder.start("end");
		System.out.println("end");
		transactionRecorder.end();
	}

	@Override
	public void terminateObject() {
		System.out.println("terminate user");
	}
}