package org.roy.loadx.priv.transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionAggregatorImpl implements TransactionAggregator {
	private final Map<String, TransactionData> transactions = new HashMap<>();

	@Override
	public synchronized void addPass(String name, double durationMilli) {
	  getOrCreateTransactionData(name).addPass(durationMilli);
	}

	@Override
	public synchronized void addFail(String name) {
	  getOrCreateTransactionData(name).addFail();
	}

	private TransactionData getOrCreateTransactionData(String name) {
    TransactionData transactionData = transactions.get(name);
    if (transactionData == null) {
      transactionData = new TransactionData();
      transactions.put(name, transactionData);
    }	  
    return transactionData;
	}
	
  @Override
	public synchronized List<String> getSortedTransactionNames() {
		List<String> transactionNames = new ArrayList<>();
		transactionNames.addAll(transactions.keySet());
		Collections.sort(transactionNames);
		return transactionNames;
	}

  @Override
	public synchronized TransactionData getTransactionData(String name) {
		return new TransactionData(transactions.get(name));
	}
}
