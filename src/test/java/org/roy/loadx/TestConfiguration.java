package org.roy.loadx;

import org.roy.loadx.transaction.TimeProvider;
import org.roy.loadx.transaction.TimeProviderImpl;
import org.roy.loadx.transaction.TransactionAggregator;
import org.roy.loadx.transaction.TransactionPrintRunner;
import org.roy.loadx.transaction.TransactionPrinterFactory;

public class TestConfiguration implements Configuration {
  private TimeProvider timeProvider = new TimeProviderImpl();
	
  public TimeProvider getTimeProvider() {
		return timeProvider;
	}

	public TransactionPrintRunner getTransactionPrintRunner() {
		return new TestTransactionPrintRunner();
	}

  @Override
  public void setTransactionPrintRunner(TransactionPrinterFactory transactionPrinterFactory,
      TransactionAggregator transactionAggregator) {
  }
}