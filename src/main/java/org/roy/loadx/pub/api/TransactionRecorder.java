package org.roy.loadx.pub.api;

public interface TransactionRecorder {
	void start(String name);

	void end();
}
