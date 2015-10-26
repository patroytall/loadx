package org.roy.loadx.transaction;

public class TransactionData {
	private long transactionCount;
	private double averageDurationMilli;

	public TransactionData() {
		this.transactionCount = 0;
		this.averageDurationMilli = 0;
	}

	public TransactionData(TransactionData transactionData) {
		transactionCount = transactionData.transactionCount;
		averageDurationMilli = transactionData.averageDurationMilli;
	}

	public TransactionData add(double durationMilli) {
		long previousTransactionCount = transactionCount;
		transactionCount++;
		averageDurationMilli = (averageDurationMilli * previousTransactionCount + durationMilli) / transactionCount;
		return this;
	}

	public double getAverageDurationMilli() {
		return averageDurationMilli;
	}

	public long getTransactionCount() {
		return transactionCount;
	}
}