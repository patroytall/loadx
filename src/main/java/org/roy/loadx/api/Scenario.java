package org.roy.loadx.api;

public interface Scenario {
	void initializeUser();

	void start();

	void run();

	void end();

	void terminateUser();
}
