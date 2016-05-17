package org.roy.loadx;

import org.roy.loadx.transaction.TimeProvider;
import org.roy.loadx.transaction.print.TransactionPrintRunner;

/**
 * All methods of this class return the same object instance when called multiple times.
 */
public interface Configuration {
  TimeProvider getTimeProvider();

  /**
   * @return Transaction runner to use instead of default.
   */
  TransactionPrintRunner getTransactionPrintRunner();
}