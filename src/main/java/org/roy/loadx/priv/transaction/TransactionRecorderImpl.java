package org.roy.loadx.priv.transaction;

import org.roy.loadx.priv.engine.TimeProvider;
import org.roy.loadx.pub.api.TransactionRecorder;

public class TransactionRecorderImpl implements TransactionRecorder {
  private final TransactionAggregator transactionAggregator;
  private final TimeProvider timeProvider;

  private long startTimeNano;
  private String name;
  private boolean transactionRunning = false;

  public TransactionRecorderImpl(TransactionAggregator transactionAggregator,
      TimeProvider timeProvider) {
    this.transactionAggregator = transactionAggregator;
    this.timeProvider = timeProvider;
  }

  @Override
  public void start(String name) {
    this.name = name;
    startTimeNano = timeProvider.nanoTime();
    transactionRunning = true;
  }

  @Override
  public void end() {
    if (!transactionRunning) {
      throw new RuntimeException("transaction not running");
    }
    long endTimeNano = timeProvider.nanoTime();
    double durationMilli = (endTimeNano - startTimeNano) / 1e6;
    transactionAggregator.addPass(name, durationMilli);
    endTransaction();
  }

  private void endTransaction() {
    transactionRunning = false;
    this.name = null;
  }

  public boolean transactionRunning() {
    return transactionRunning;
  }
  
  public String getTransactionName() {
    return name;
  }
  
  public void abort() {
    if (transactionRunning) {
      transactionAggregator.addFail(name);
    }
    endTransaction();
  }
}