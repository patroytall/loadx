package org.roy.loadx;

import org.roy.loadx.transaction.TimeProvider;
import org.roy.loadx.transaction.TimeProviderImpl;
import org.roy.loadx.transaction.TransactionPrintRunner;

public class TestConfiguration implements Configuration {
  private TimeProvider timeProvider = new TimeProviderImpl();
	
  public TimeProvider getTimeProvider() {
		return timeProvider;
	}

	public TransactionPrintRunner getTransactionPrintRunner() {
		return new TestTransactionPrintRunner();
	}
}