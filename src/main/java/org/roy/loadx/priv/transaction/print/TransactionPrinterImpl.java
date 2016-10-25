package org.roy.loadx.priv.transaction.print;

import org.roy.loadx.priv.transaction.TransactionAggregator;
import org.roy.loadx.priv.transaction.TransactionData;

public class TransactionPrinterImpl implements TransactionPrinter {

  private final TransactionAggregator transactionAggregator;

  TransactionPrinterImpl(TransactionAggregator transactionAggregator) {
    this.transactionAggregator = transactionAggregator;
  }

  @Override
  public void print() {
    TablePrinter tablePrinter =
        new TablePrinter("Transaction", "Min", "Max", "Average", "StdDev", "90%", "Pass", "Fail");
    for (String name : transactionAggregator.getSortedTransactionNames()) {
      TransactionData transactionData = transactionAggregator.getTransactionData(name);
      String min = String.valueOf(Math.round(transactionData.getMinDurationMilli()));
      String max = String.valueOf(Math.round(transactionData.getMaxDurationMilli()));
      String average = String.valueOf(Math.round(transactionData.getAverageDurationMilli()));
      String standardDeviation =
          String.valueOf(Math.round(transactionData.getStandardDeviation()));
      String ninetyPercentileEstimate =
          String.valueOf(Math.round(transactionData.get90PercentileEstimate()));
      String pass = String.valueOf(transactionData.getPassCount());
      String fail = String.valueOf(transactionData.getFailCount());
      tablePrinter.addRow(name, min, max, average, standardDeviation, ninetyPercentileEstimate,
          pass, fail);
    }
    tablePrinter.print();
    System.out.println();
  }
}