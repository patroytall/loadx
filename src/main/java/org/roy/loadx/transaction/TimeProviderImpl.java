package org.roy.loadx.transaction;

import org.springframework.stereotype.Component;

@Component
public class TimeProviderImpl implements TimeProvider {
	public long nanoTime() {
		return System.nanoTime();
	}
}