package org.roy.loadx.priv.transaction.print;

import org.roy.loadx.priv.transaction.TransactionAggregator;

public class TransactionPrinterFactoryImpl implements TransactionPrinterFactory {

  @Override
  public TransactionPrinter getInstance(TransactionAggregator transactionAggregator) {
    return new TransactionPrinterImpl(transactionAggregator);
  }
}