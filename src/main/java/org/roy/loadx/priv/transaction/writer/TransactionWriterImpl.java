package org.roy.loadx.priv.transaction.writer;

import static org.roy.loadx.priv.Util.throwUnchecked;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class TransactionWriterImpl implements TransactionWriter {
  private final Map<String, PrintWriter> printWriters = new HashMap<>();

  @Override
  public void addFail(String transactionName, double relativeStartTimeMillis) {
    PrintWriter printWriter = getPrintWriter(transactionName);
    printWriter.print(" " + relativeStartTimeMillis + ":-1");
  }

  @Override
  public void addPass(String transactionName, double relativeStartTimeMillis, double durationMillis) {
    PrintWriter printWriter = getPrintWriter(transactionName);
    printWriter.println(" " + relativeStartTimeMillis + ":" + durationMillis);
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
