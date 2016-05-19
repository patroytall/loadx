package org.roy.loadx.priv.engine;

import org.roy.loadx.priv.transaction.TimeProvider;
import org.roy.loadx.priv.transaction.TimeProviderImpl;
import org.roy.loadx.priv.transaction.print.TransactionPrintRunnerThread;

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