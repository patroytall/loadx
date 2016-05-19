package org.roy.loadx.priv.transaction.print;

import org.roy.loadx.priv.transaction.TransactionAggregator;

public interface TransactionPrinterFactory {
  TransactionPrinter getInstance(TransactionAggregator transactionAggregator);
}
