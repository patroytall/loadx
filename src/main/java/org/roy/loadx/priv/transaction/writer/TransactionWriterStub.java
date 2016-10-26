package org.roy.loadx.priv.transaction.writer;

public class TransactionWriterStub implements TransactionWriter {
  @Override
  public void addFail(String transactionName) {
  }

  @Override
  public void addPass(String transactionName, double durationMilli) {
  }

  @Override
  public void terminate() {
  }
}