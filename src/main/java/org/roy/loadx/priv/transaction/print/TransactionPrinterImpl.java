package org.roy.loadx.priv.transaction.print;

import org.apache.commons.lang.StringUtils;
import org.roy.loadx.priv.transaction.TransactionAggregator;
import org.roy.loadx.priv.transaction.TransactionData;

import java.util.ArrayList;

public class TransactionPrinterImpl implements TransactionPrinter {

  /**
   * Immutable.
   */
  static private class TransactionDataSummaryEntry {
    public final String transactionName;
    public final String average;
    public final String pass;
    public final String fail;

    public TransactionDataSummaryEntry(String transactionName, String average, String pass,
        String fail) {
      this.transactionName = transactionName;
      this.average = average;
      this.pass = pass;
      this.fail = fail;
    }
  }

  static private class TransactionDataSummary {
    static private final String TRANSACTION_HEADER = "Transaction";
    static private final String AVERAGE_HEADER = "Average";
    static private final String PASS_HEADER = "Pass";
    static private final String FAIL_HEADER = "Fail";
    static private final int MAX_ROW_WIDTH = 120;
    static private final int CONSOLE_MAX_ROW_WIDTH = MAX_ROW_WIDTH - 1;
    static private final String ROW_FORMAT = "%%-%ds | %%10s | %%10s | %%10s%n";
    static private final int PRE_ALLOCATED_SPACE_PER_ROW = 26;
    static private final String ELLIPSIS = "...";

    private int longestTransactionNameLength;
    private ArrayList<TransactionDataSummaryEntry> rows = new ArrayList<>();

    public TransactionDataSummary() {
      longestTransactionNameLength = TRANSACTION_HEADER.length();
    }

    public void add(TransactionDataSummaryEntry entry) {
      rows.add(entry);
      int transactionNameLength = entry.transactionName.length();
      if (transactionNameLength > longestTransactionNameLength) {
        longestTransactionNameLength = transactionNameLength;
      }
    }

    @Override
    public String toString() {
      int transactionColumnWidth = getTransactionColumnWidth();
      String rowFormat = computeDynamicFormatString();
      StringBuilder sb = new StringBuilder();
      sb.append(String.format(rowFormat, TRANSACTION_HEADER, AVERAGE_HEADER, PASS_HEADER,
          FAIL_HEADER));
      sb.append(String.format(rowFormat, StringUtils.repeat("=", transactionColumnWidth),
          StringUtils.repeat("=", AVERAGE_HEADER.length()),
          StringUtils.repeat("=", PASS_HEADER.length()),
          StringUtils.repeat("=", FAIL_HEADER.length())));
      for (TransactionDataSummaryEntry entry : rows) {
        sb.append(String.format(rowFormat, getTransactionDisplayName(entry.transactionName),
            entry.average, entry.pass, entry.fail));
      }
      return sb.toString();
    }

    private String computeDynamicFormatString() {
      return String.format(ROW_FORMAT, getTransactionColumnWidth());
    }

    private int getTransactionColumnWidth() {
      return Math.min(CONSOLE_MAX_ROW_WIDTH - PRE_ALLOCATED_SPACE_PER_ROW,
          longestTransactionNameLength);
    }

    private String getTransactionDisplayName(String transactionName) {
      int transactionColumnWidth = getTransactionColumnWidth();
      return transactionName.length() > transactionColumnWidth ? transactionName.substring(0,
          transactionColumnWidth - ELLIPSIS.length()) + ELLIPSIS : transactionName;
    }
  }

  private final TransactionAggregator transactionAggregator;

  static private void println(String str) {
    System.out.println(str);
    System.out.flush();
  }

  TransactionPrinterImpl(TransactionAggregator transactionAggregator) {
    this.transactionAggregator = transactionAggregator;
  }

  @Override
  public void print() {
    TransactionDataSummary summary = new TransactionDataSummary();
    for (String name : transactionAggregator.getSortedTransactionNames()) {
      TransactionData transactionData = transactionAggregator.getTransactionData(name);
      long roundedAverage = Math.round(transactionData.getAverageDurationMilli());
      String average = String.valueOf(roundedAverage);
      String pass = String.valueOf(transactionData.getPassCount());
      String fail = String.valueOf(transactionData.getFailCount());
      summary.add(new TransactionDataSummaryEntry(name, average, pass, fail));
    }
    println(summary.toString());
  }
}