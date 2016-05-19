package org.roy.loadx.priv.transaction.print;

import org.roy.loadx.priv.transaction.TransactionAggregatorImpl;

public class TransactionPrinterFactoryImpl implements TransactionPrinterFactory {

  @Override
  public TransactionPrinter getInstance(TransactionAggregatorImpl transactionAggregator) {
    return new TransactionPrinterImpl(transactionAggregator);
  }
}