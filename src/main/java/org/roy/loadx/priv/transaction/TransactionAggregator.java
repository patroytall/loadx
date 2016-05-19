package org.roy.loadx.priv.transaction;

import java.util.List;

public interface TransactionAggregator {
  void addTransaction(String name, double durationMilli);
  List<String> getSortedTransactionNames();
  TransactionData getTransactionData(String name);
}