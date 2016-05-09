package org.roy.loadx;

import org.roy.loadx.transaction.TimeProvider;
import org.roy.loadx.transaction.TransactionPrintRunner;

public interface Configuration {
  /**
   * Get the time provider.
   * 
   * @return always the same object
   */
  TimeProvider getTimeProvider();

  /**
   * Get the transaction print runner.
   * 
   * @return always the same object
   */
  TransactionPrintRunner getTransactionPrintRunner();
}