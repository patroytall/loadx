package org.roy.loadx.transaction.print;

import org.roy.loadx.transaction.TransactionAggregator;

public interface TransactionPrinterFactory {
  TransactionPrinter getInstance(TransactionAggregator transactionAggregator);
}
