package org.roy.loadx;

import org.roy.loadx.priv.transaction.TimeProvider;

public class TestTimeProvider implements TimeProvider {
	public static final double TIME_DELTA = 1e-12;

	private long currentTimeNano = 0;

	@Override
	public long nanoTime() {
		return currentTimeNano;
	}

	@Override
	public void sleep(long millis, int nanos) {
		currentTimeNano += millis * 1e6 + nanos;
	}
}
