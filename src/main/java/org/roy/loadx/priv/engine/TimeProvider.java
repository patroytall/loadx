package org.roy.loadx.priv.engine;

/**
 * Thread safe.
 */
public interface TimeProvider {
	long nanoTime();

	void sleep(long millis, int nanos);
}