package org.roy.loadx.priv.transaction;

/**
 * Implementations must be thread safe.
 */
public interface TransactionListener {
  void addFail(String transactionName);
  void addPass(String transactionName, double durationMilli);
}