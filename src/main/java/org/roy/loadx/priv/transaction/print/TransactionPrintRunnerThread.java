package org.roy.loadx.priv.transaction.print;

public class TransactionPrintRunnerThread extends Thread implements TransactionPrintRunner {
  private static final long PRINT_INTERVAL_TIME_SECONDS = 3;

  private Thread runThread;
  private final TransactionPrinter transactionPrinter;

  private boolean done = false;

  public TransactionPrintRunnerThread(TransactionPrinter transactionPrinter) {
    this.transactionPrinter = transactionPrinter;
  }

  @Override
  public void run() {
    runThread = Thread.currentThread();
    try {
      runThread();
    } catch (Throwable t) {
      throw new RuntimeException(
          "Transaction printer aborted. Transactions will not be printed anymore.", t);
    }
  }

  private void runThread() {
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
  }

  private synchronized void printTransactions() {
    transactionPrinter.print();
  }

  @Override
  public synchronized void done() {
    done = true;
    if (runThread != null) {
      printTransactions();
      runThread.interrupt();
    }
  }
}