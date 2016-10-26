package org.roy.loadx.priv.transaction.writer;

import static org.roy.loadx.priv.Util.throwUnchecked;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class TransactionWriterImpl implements TransactionWriter {
  private final Map<String, PrintWriter> printWriters = new HashMap<>();
  private final long startTime = System.currentTimeMillis() /1000;

  @Override
  public void addFail(String transactionName) {
    PrintWriter printWriter = getPrintWriter(transactionName);
    printWriter.print(" -1");
  }

  @Override
  public void addPass(String transactionName, double durationMilli) {
    PrintWriter printWriter = getPrintWriter(transactionName);
    printWriter.println((long) (System.currentTimeMillis() / 1000 - startTime) + ", " + durationMilli);
    printWriter.flush();
  }

  private synchronized PrintWriter getPrintWriter(String transactionName) {
    PrintWriter printWriter = printWriters.get(transactionName);
    if (printWriter == null) {
      printWriter = throwUnchecked(() -> new PrintWriter(new FileWriter(transactionName, true)));
      printWriters.put(transactionName, printWriter);
    }
    return printWriter;
  }

  @Override
  public synchronized void terminate() {
    for (PrintWriter printWriter : printWriters.values()) {
      printWriter.close();
    }
  }
}
