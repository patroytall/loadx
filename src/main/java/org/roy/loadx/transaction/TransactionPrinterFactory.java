package org.roy.loadx.transaction;

public interface TransactionPrinterFactory {
  TransactionPrinter getInstance(TransactionAggregator transactionAggregator);
}
