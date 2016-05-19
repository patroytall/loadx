package org.roy.loadx.priv.transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionAggregatorImpl implements TransactionAggregator {
	private final Map<String, TransactionData> transactions = new HashMap<>();

	public synchronized void addTransaction(String name, double durationMilli) {
		TransactionData transactionData = transactions.get(name);
		if (transactionData == null) {
			transactionData = new TransactionData();
			transactions.put(name, transactionData);
		}
		transactionData.add(durationMilli);
	}

	public synchronized List<String> getSortedTransactionNames() {
		List<String> transactionNames = new ArrayList<>();
		transactionNames.addAll(transactions.keySet());
		Collections.sort(transactionNames);
		return transactionNames;
	}

	public synchronized TransactionData getTransactionData(String name) {
		return new TransactionData(transactions.get(name));
	}
}
