package org.roy.loadx.priv.engine;

import org.roy.loadx.priv.engine.Configuration;
import org.roy.loadx.priv.engine.time.TimeProvider;
import org.roy.loadx.priv.engine.time.TimeProviderImpl;
import org.roy.loadx.priv.transaction.print.TestTransactionPrintRunner;
import org.roy.loadx.priv.transaction.print.TransactionPrintRunner;

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