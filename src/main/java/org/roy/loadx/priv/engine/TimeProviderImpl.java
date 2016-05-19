package org.roy.loadx.priv.engine;

/**
 * Thread safe.
 */
public class TimeProviderImpl implements TimeProvider {
	public long nanoTime() {
		return System.nanoTime();
	}

	@Override
	public void sleep(long millis, int nanos) {
		try {
			Thread.sleep(millis, nanos);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}