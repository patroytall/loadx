package org.roy.loadx.transaction;

public class TransactionPrinterFactoryImpl implements TransactionPrinterFactory {

  @Override
  public TransactionPrinter getInstance(TransactionAggregator transactionAggregator) {
    return new TransactionPrinterImpl(transactionAggregator);
  }
}