package org.roy.loadx.priv.transaction;

import java.util.List;

public interface TransactionAggregator {
  void addPass(String name, double durationMilli);
  void addFail(String name);
  List<String> getSortedTransactionNames();
  TransactionData getTransactionData(String name);
}