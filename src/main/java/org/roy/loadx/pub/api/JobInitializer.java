package org.roy.loadx.pub.api;

public interface JobInitializer {
	void initializeJob(ExecutionData jobData);

	void terminateJob();
}
