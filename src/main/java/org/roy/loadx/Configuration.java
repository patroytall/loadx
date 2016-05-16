package org.roy.loadx;

import org.roy.loadx.transaction.TimeProvider;
import org.roy.loadx.transaction.TransactionAggregator;
import org.roy.loadx.transaction.TransactionPrintRunner;
import org.roy.loadx.transaction.TransactionPrinterFactory;

public interface Configuration {
  /**
   * Get the time provider.
   * 
   * @return always the same object
   */
  TimeProvider getTimeProvider();

  TransactionPrintRunner getTransactionPrintRunner();

  void setTransactionPrintRunner(TransactionPrinterFactory transactionPrinterFactory,
      TransactionAggregator transactionAggregator);
}