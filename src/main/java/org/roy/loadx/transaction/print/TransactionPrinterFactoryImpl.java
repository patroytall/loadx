package org.roy.loadx.transaction.print;

import org.roy.loadx.transaction.TransactionAggregator;

public class TransactionPrinterFactoryImpl implements TransactionPrinterFactory {

  @Override
  public TransactionPrinter getInstance(TransactionAggregator transactionAggregator) {
    return new TransactionPrinterImpl(transactionAggregator);
  }
}