package org.roy.loadx.api;

public interface JobInitializer {
	void initializeJob(ExecutionData jobData);

	void terminateJob(ExecutionData jobData);
}
