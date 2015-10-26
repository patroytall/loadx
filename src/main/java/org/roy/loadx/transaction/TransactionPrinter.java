package org.roy.loadx.transaction;

public class TransactionPrinter {
	private final TransactionAggregator transactionAggregator;

	public TransactionPrinter(TransactionAggregator transactionAggregator) {
		this.transactionAggregator = transactionAggregator;
	}

	public void print() {
		for (String name : transactionAggregator.getSortedTransactionNames()) {
			System.out.println(name + " " + transactionAggregator.getTransactionAverageDurationMilli(name));
		}
	}
}
