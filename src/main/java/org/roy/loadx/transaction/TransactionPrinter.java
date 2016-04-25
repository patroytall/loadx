package org.roy.loadx.transaction;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

public class TransactionPrinter {
	static private class TransactionDataSummaryEntry {
		private String transactionName;
		private String average;
		private String count;
		
		public TransactionDataSummaryEntry(String transactionName, String average, String count) {
			this.transactionName = transactionName;
			this.average = average;
			this.count = count;
		}

		public String getTransactionName() {
			return transactionName;
		}

		public String getAverage() {
			return average;
		}

		public String getCount() {
			return count;
		}
	}
	
	static private class TransactionDataSummary {
		static private final String TRANSACTION_HEADER = "Transaction";
		static private final String AVERAGE_HEADER = "Average";
		static private final String COUNT_HEADER = "Count";
		static private final int MAX_ROW_WIDTH = 78;
		static private final String ROW_FORMAT = "%%-%ds | %%10s | %%10s%n";
		static private final int PRE_ALLOCATED_SPACE_PER_ROW = 26;
		
		private int maxTransactionNameLength;
		private ArrayList<TransactionDataSummaryEntry> rows;
		
		public TransactionDataSummary() {
			maxTransactionNameLength = TRANSACTION_HEADER.length();
			rows = new ArrayList<TransactionDataSummaryEntry>();
		}
		
		public void add(TransactionDataSummaryEntry entry) {
			rows.add(entry);
			int txNameLength = entry.getTransactionName().length();
			if (txNameLength > maxTransactionNameLength) {
				maxTransactionNameLength = txNameLength;
			}
		}
		
		@Override
		public String toString() {
			int txColumnWidth = getTransactionColumnWidth();
			String rowFormat = computeDynamicFormatString();
			StringBuilder sb = new StringBuilder();
			sb.append(String.format(rowFormat, TRANSACTION_HEADER, AVERAGE_HEADER, COUNT_HEADER));
			sb.append(String.format(rowFormat, StringUtils.repeat("=", txColumnWidth), StringUtils.repeat("=", AVERAGE_HEADER.length()), StringUtils.repeat("=", COUNT_HEADER.length())));
			for (TransactionDataSummaryEntry entry : rows) {
				String name = entry.getTransactionName();
				if (name.length() > txColumnWidth) {
					name = name.substring(0, txColumnWidth - 3) + "...";
				}
				sb.append(String.format(rowFormat, name, entry.getAverage(), entry.getCount()));
			}
			return sb.toString();
		}
		
		private String computeDynamicFormatString() {
			return String.format(ROW_FORMAT, getTransactionColumnWidth());
		}
		
		private int getTransactionColumnWidth() {
			return Math.min(MAX_ROW_WIDTH - PRE_ALLOCATED_SPACE_PER_ROW, maxTransactionNameLength);
		}
	}
	
	private final TransactionAggregator transactionAggregator;

	public TransactionPrinter(TransactionAggregator transactionAggregator) {
		this.transactionAggregator = transactionAggregator;
	}

	public void print() {
		TransactionDataSummary summary = new TransactionDataSummary();
		for (String name : transactionAggregator.getSortedTransactionNames()) {
			TransactionData transactionData = transactionAggregator.getTransactionData(name);
			long roundedAverage = Math.round(transactionData.getAverageDurationMilli());
			String average = String.valueOf(roundedAverage);
			String count = String.valueOf(transactionData.getTransactionCount());
			summary.add(new TransactionDataSummaryEntry(name, average, count));
		}
		System.out.println(summary);
		System.out.flush();
		System.out.println();
	}
}
