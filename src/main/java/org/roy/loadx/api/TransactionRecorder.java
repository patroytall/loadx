package org.roy.loadx.api;

public interface TransactionRecorder {
	void start(String name);

	void end();
}
