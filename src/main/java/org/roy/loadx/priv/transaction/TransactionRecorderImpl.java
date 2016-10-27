package org.roy.loadx.priv.transaction;

import org.roy.loadx.priv.engine.time.TimeHandler;
import org.roy.loadx.pub.api.TransactionRecorder;

import java.util.List;

public class TransactionRecorderImpl implements TransactionRecorder {
  private final TimeHandler timeHandler;
  private final List<TransactionListener> transactionListeners;
  
  private long startTimeNano;
  private String name;
  private boolean transactionRunning = false;
  private double relativeStartTime;

  public TransactionRecorderImpl(List<TransactionListener> transactionListeners,
      TimeHandler timeHandler) {
    this.transactionListeners = transactionListeners;
    this.timeHandler = timeHandler;
  }

  @Override
  public void start(String name) {
    this.name = name;
    startTimeNano = timeHandler.getTimeProvider().nanoTime();
    transactionRunning = true;
    relativeStartTime = TimeHandler.nanoTimeToMillis(timeHandler.getRelativeNanoTime());
  }

  @Override
  public void end() {
    if (!transactionRunning) {
      throw new RuntimeException("transaction not running");
    }
    long endTimeNano = timeHandler.getTimeProvider().nanoTime();
    double durationMilli = TimeHandler.nanoTimeToMillis(endTimeNano - startTimeNano);
    for (TransactionListener transactionListener : transactionListeners) {
      transactionListener.addPass(name, relativeStartTime, durationMilli);
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
        transactionListener.addFail(name, relativeStartTime);
      }
    }
    endTransaction();
  }
}