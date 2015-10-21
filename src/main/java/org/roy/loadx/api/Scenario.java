package org.roy.loadx.api;

public interface Scenario {
	void initializeObject(ExecutionData scenarioClassData, TransactionRecorder transactionRecorder);

	void start();

	void run();

	void end();

	void terminateObject();
}
