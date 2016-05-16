package org.roy.loadx;

import org.roy.loadx.transaction.TimeProvider;
import org.roy.loadx.transaction.TimeProviderImpl;
import org.roy.loadx.transaction.TransactionAggregator;
import org.roy.loadx.transaction.TransactionPrintRunner;
import org.roy.loadx.transaction.TransactionPrintRunnerImpl;
import org.roy.loadx.transaction.TransactionPrinterFactory;

public class EngineConfiguration implements Configuration {
  private final TimeProvider timeProvider = new TimeProviderImpl();

  private TransactionPrintRunner transactionPrintRunner;

  public TimeProvider getTimeProvider() {
    return timeProvider;
  }

  public void setTransactionPrintRunner(TransactionPrinterFactory transactionPrinterFactory,
      TransactionAggregator transactionAggregator) {
    transactionPrintRunner = new TransactionPrintRunnerImpl(
        transactionPrinterFactory.getInstance(transactionAggregator));
  }

  public TransactionPrintRunner getTransactionPrintRunner() {
    return transactionPrintRunner;
  }
}