package org.roy.loadx.priv.transaction.grapher;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.roy.loadx.priv.Util;
import org.roy.loadx.priv.transaction.TransactionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

public class TransactionGrapher implements TransactionListener {
  private static class Transaction {
    public double durationMilli;

    public Transaction(double durationMilli) {
      this.durationMilli = durationMilli;
    }
  }

  private static final ObjectMapper objectMapper = new ObjectMapper();

  private SortedMap<String, List<Transaction>> transactionsMap = new TreeMap<>();

  @Override
  public void addFail(String transactionName, double relativeStartTimeMillis) {
    // failed transactions are ignored
  }

  @Override
  public synchronized void addPass(String transactionName, double relativeStartTimeMillis,
      double durationMilli) {
    List<Transaction> transactions = getTransactions(transactionName);
    if (transactions.size() == 10000000) {
      throw new RuntimeException("maximum of 10,000,000 transactions reached");
    }
    transactions.add(new Transaction(durationMilli));
  }

  private List<Transaction> getTransactions(String transactionName) {
    List<Transaction> transactions = transactionsMap.get(transactionName);
    if (transactions == null) {
      transactions = new ArrayList<>();
      transactionsMap.put(transactionName, transactions);
    }
    return transactions;
  }

  public String getJson() {
    GraphInfo graphInfo = new GraphInfo();

    for (Entry<String, List<Transaction>> entry : transactionsMap.entrySet()) {
      List<Double> durationsMilli = new ArrayList<>();
      for (Transaction transaction : entry.getValue()) {
        durationsMilli.add(transaction.durationMilli);
      }
      GraphInfo.TransactionInfo transactionInfo =
          new GraphInfo.TransactionInfo(entry.getKey(), durationsMilli);
      graphInfo.transactions.add(transactionInfo);
    }

    return Util.throwUnchecked(() -> objectMapper.writeValueAsString(graphInfo));
  }
}