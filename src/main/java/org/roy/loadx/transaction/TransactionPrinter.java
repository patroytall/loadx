package org.roy.loadx.transaction;

import java.util.ArrayList;
import java.util.List;

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
		List<String[]> output = new ArrayList<>();
		output.add(new String[] {"Transaction", "Average", "Count"});
		int maxTxLength = 11; // length of 'Transaction'
		
		for (String name : transactionAggregator.getSortedTransactionNames()) {
			TransactionData transactionData = transactionAggregator.getTransactionData(name);
			long roundedAverage = Math.round(transactionData.getAverageDurationMilli());
			String average = String.valueOf(roundedAverage);
			String count = String.valueOf(transactionData.getTransactionCount());
			output.add(new String[] {name, average, count});
			if(name.length() > maxTxLength)
				maxTxLength = name.length();
		}
		printFormatted(output, maxTxLength);
	}

	private void printFormatted(final List<String[]> data, final int maxTxLength) {
		int rowWidth = 78; // adjust to taste
		int txWidth = Math.min(rowWidth - 26, maxTxLength); // 26 is based on space needed for other cols using existing format
		
		// insert the header separator based on the dynamically generated length
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < txWidth; ++i)
			sb.append("=");
		data.add(1, new String[] {sb.toString(), "==========", "=========="});

		String fmtString = String.format("%%-%ds | %%10s | %%10s", txWidth);
		for(String[] row : data) {
			if(row[0].length() > txWidth)
				row[0] = row[0].substring(0, txWidth - 3) + "...";
			format(fmtString, row[0], row[1], row[2]);
		}
		println();
	}
}
