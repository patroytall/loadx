package org.roy.loadx.transaction;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

public class TransactionPrinter {
	static private class TransactionDataSummaryEntry {
		private final String transactionName;
		private final String average;
		private final String count;
		
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
		static private final int MAX_ROW_WIDTH = 120;
		static private final int CONSOLE_MAX_ROW_WIDTH = MAX_ROW_WIDTH - 1;
		static private final String ROW_FORMAT = "%%-%ds | %%10s | %%10s%n";
		static private final int PRE_ALLOCATED_SPACE_PER_ROW = 26;
		static private final String ELLIPSIS = "...";
		
		private int longestTransactionNameLength;
		private ArrayList<TransactionDataSummaryEntry> rows;
		
		public TransactionDataSummary() {
			longestTransactionNameLength = TRANSACTION_HEADER.length();
			rows = new ArrayList<>();
		}
		
		public void add(TransactionDataSummaryEntry entry) {
			rows.add(entry);
			int transactionNameLength = entry.getTransactionName().length();
			if (transactionNameLength > longestTransactionNameLength) {
				longestTransactionNameLength = transactionNameLength;
			}
		}
		
		@Override
		public String toString() {
			int transactionColumnWidth = getTransactionColumnWidth();
			String rowFormat = computeDynamicFormatString();
			StringBuilder sb = new StringBuilder();
			sb.append(String.format(rowFormat, TRANSACTION_HEADER, AVERAGE_HEADER, COUNT_HEADER));
			sb.append(String.format(rowFormat, StringUtils.repeat("=", transactionColumnWidth), StringUtils.repeat("=", AVERAGE_HEADER.length()), StringUtils.repeat("=", COUNT_HEADER.length())));
			for (TransactionDataSummaryEntry entry : rows) {
				sb.append(String.format(rowFormat, getTransactionDisplayName(entry.getTransactionName()), entry.getAverage(), entry.getCount()));
			}
			return sb.toString();
		}
		
		private String computeDynamicFormatString() {
			return String.format(ROW_FORMAT, getTransactionColumnWidth());
		}
		
		private int getTransactionColumnWidth() {
			return Math.min(CONSOLE_MAX_ROW_WIDTH - PRE_ALLOCATED_SPACE_PER_ROW, longestTransactionNameLength);
		}
		
		private String getTransactionDisplayName(String transactionName) {
			int transactionColumnWidth = getTransactionColumnWidth();
			return transactionName.length() > transactionColumnWidth ? transactionName.substring(0, transactionColumnWidth - ELLIPSIS.length()) + ELLIPSIS : transactionName;
		}
	}
	
	static private void println(String str) {
		System.out.println(str);
		System.out.flush();
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
		println(summary.toString());
	}
}
