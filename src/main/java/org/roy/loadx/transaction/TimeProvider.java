package org.roy.loadx.transaction;

public interface TimeProvider {
	long nanoTime();

	void sleep(long millis, int nanos);
}
