package org.roy.loadx.priv.transaction;

/**
 * Thread safe.
 */
public interface TimeProvider {
	long nanoTime();

	void sleep(long millis, int nanos);
}
