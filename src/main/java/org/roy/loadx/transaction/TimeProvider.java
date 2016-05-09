package org.roy.loadx.transaction;

/**
 * Thread safe.
 */
public interface TimeProvider {
	long nanoTime();

	void sleep(long millis, int nanos);
}
