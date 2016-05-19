package org.roy.loadx.priv.transaction.print;

import org.roy.loadx.priv.transaction.TransactionAggregatorImpl;

public interface TransactionPrinterFactory {
  TransactionPrinter getInstance(TransactionAggregatorImpl transactionAggregator);
}
