package org.roy.loadx.transaction;

public class TimeProviderImpl implements TimeProvider {
	public long nanoTime() {
		return System.nanoTime();
	}
}