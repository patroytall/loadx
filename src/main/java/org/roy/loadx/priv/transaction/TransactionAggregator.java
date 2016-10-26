package org.roy.loadx.priv.transaction;

import java.util.List;

public interface TransactionAggregator extends TransactionListener {
  List<String> getSortedTransactionNames();
  TransactionData getTransactionData(String name);
}