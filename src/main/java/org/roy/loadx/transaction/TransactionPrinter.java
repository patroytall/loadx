package org.roy.loadx.transaction;

public class TransactionPrinter {
	private final TransactionAggregator transactionAggregator;

	public TransactionPrinter(TransactionAggregator transactionAggregator) {
		this.transactionAggregator = transactionAggregator;
	}

	private static void println() {
		System.out.println();
		System.out.flush();
	}

	private static void format(String format, Object... args) {
		System.out.format(format, args);
		System.out.flush();
		println();
	}

	public void print() {
		printFormatted("Transaction", "Average", "Count");
		printFormatted("===========", "=======", "=====");
		for (String name : transactionAggregator.getSortedTransactionNames()) {
			TransactionData transactionData = transactionAggregator.getTransactionData(name);
			long roundedAverage = Math.round(transactionData.getAverageDurationMilli());
			String average = String.valueOf(roundedAverage);
			String count = String.valueOf(transactionData.getTransactionCount());
			printFormatted(name, average, count);
		}
		println();
	}

	private void printFormatted(String name, String count, String average) {
		format("%-15s | %10s | %10s", name, count, average);
	}
}
