package org.roy.loadx;

import org.roy.loadx.transaction.TimeProvider;
import org.roy.loadx.transaction.TimeProviderImpl;
import org.roy.loadx.transaction.TransactionPrintRunner;

public class TestConfiguration implements Configuration {
  private TimeProvider timeProvider = new TimeProviderImpl();
	
  @Override
  public TimeProvider getTimeProvider() {
		return timeProvider;
	}

  @Override
	public TransactionPrintRunner getTransactionPrintRunner() {
		return new TestTransactionPrintRunner();
	}
}