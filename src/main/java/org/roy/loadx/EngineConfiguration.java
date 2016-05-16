package org.roy.loadx;

import org.roy.loadx.transaction.TimeProvider;
import org.roy.loadx.transaction.TimeProviderImpl;
import org.roy.loadx.transaction.TransactionPrintRunnerThread;

public class EngineConfiguration implements Configuration {
  private final TimeProvider timeProvider = new TimeProviderImpl();

  public TimeProvider getTimeProvider() {
    return timeProvider;
  }

  @Override
  public TransactionPrintRunnerThread getTransactionPrintRunner() {
    return null;
  }
}