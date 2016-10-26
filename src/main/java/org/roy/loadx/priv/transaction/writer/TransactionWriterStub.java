package org.roy.loadx.priv.transaction.writer;

public class TransactionWriterStub implements TransactionWriter {
  @Override
  public void addFail(String transactionName, double relativeStartTimeMillis) {
  }

  @Override
  public void
      addPass(String transactionName, double relativeStartTimeMillis, double durationMilli) {
  }

  @Override
  public void terminate() {
  }
}