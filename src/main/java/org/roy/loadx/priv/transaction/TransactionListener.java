package org.roy.loadx.priv.transaction;

/**
 * Implementations must be thread safe.
 */
public interface TransactionListener {
  void addFail(String transactionName, double relativeStartTimeMillis);

  void addPass(String transactionName, double relativeStartTimeMillis, double durationMilli);
}