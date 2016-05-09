package org.roy.loadx;

import org.roy.loadx.transaction.TimeProvider;
import org.roy.loadx.transaction.TimeProviderImpl;
import org.roy.loadx.transaction.TransactionPrintRunner;
import org.roy.loadx.transaction.TransactionPrintRunnerImpl;

public class EngineConfiguration implements Configuration {
  private final TimeProvider timeProvider = new TimeProviderImpl();
  private final TransactionPrintRunner transactionPrintRunner = new TransactionPrintRunnerImpl();

  public TimeProvider getTimeProvider() {
    return timeProvider;
  }

  public TransactionPrintRunner getTransactionPrintRunner() {
    return transactionPrintRunner;
  }
}