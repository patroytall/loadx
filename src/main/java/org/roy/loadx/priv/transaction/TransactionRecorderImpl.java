package org.roy.loadx.priv.transaction;

import org.roy.loadx.priv.engine.TimeProvider;
import org.roy.loadx.pub.api.TransactionRecorder;

import java.util.List;

public class TransactionRecorderImpl implements TransactionRecorder {
  private final TimeProvider timeProvider;
  private final List<TransactionListener> transactionListeners;
  
  private long startTimeNano;
  private String name;
  private boolean transactionRunning = false;

  public TransactionRecorderImpl(List<TransactionListener> transactionListeners,
      TimeProvider timeProvider) {
    this.transactionListeners = transactionListeners;
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
    for (TransactionListener transactionListener : transactionListeners) {
      transactionListener.addPass(name, durationMilli);
    }
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
      for (TransactionListener transactionListener : transactionListeners) {
        transactionListener.addFail(name);
      }
    }
    endTransaction();
  }
}