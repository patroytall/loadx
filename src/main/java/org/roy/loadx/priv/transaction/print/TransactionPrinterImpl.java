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
    TablePrinter tablePrinter = new TablePrinter("Transaction", "Average", "Pass", "Fail");
    for (String name : transactionAggregator.getSortedTransactionNames()) {
      TransactionData transactionData = transactionAggregator.getTransactionData(name);
      long roundedAverage = Math.round(transactionData.getAverageDurationMilli());
      String average = String.valueOf(roundedAverage);
      String pass = String.valueOf(transactionData.getPassCount());
      String fail = String.valueOf(transactionData.getFailCount());
      tablePrinter.addRow(name, average, pass, fail);
    }
    tablePrinter.print();
  }
}