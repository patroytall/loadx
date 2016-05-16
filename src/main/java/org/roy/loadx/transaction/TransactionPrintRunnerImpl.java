package org.roy.loadx.transaction;

public class TransactionPrintRunnerImpl implements TransactionPrintRunner {
  private static final long PRINT_INTERVAL_TIME_SECONDS = 3;

  private final Thread runThread = Thread.currentThread();
  private final TransactionPrinter transactionPrinter;

  private boolean done = false;

  public TransactionPrintRunnerImpl(TransactionPrinter transactionPrinter) {
    this.transactionPrinter = transactionPrinter;
  }

  @Override
  public void run() {
    while (!done) {
      printTransactions();
      try {
        Thread.sleep(PRINT_INTERVAL_TIME_SECONDS * 1000);
      } catch (InterruptedException e) {
        if (!done) {
          throw new RuntimeException("invalid not done interruption");
        }
      }
    }
    printTransactions();
  }

  private void printTransactions() {
    transactionPrinter.print();
  }

  @Override
  public void stop() {
    done = true;
    runThread.interrupt();
  }
}
