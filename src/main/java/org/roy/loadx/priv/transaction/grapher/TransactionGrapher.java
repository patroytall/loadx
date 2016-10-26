package org.roy.loadx.priv.transaction.grapher;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.roy.loadx.priv.Util;
import org.roy.loadx.priv.transaction.TransactionListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

public class TransactionGrapher implements TransactionListener {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  private double maxDurationMillis = Double.MIN_VALUE;
  
  private SortedMap<String, List<GraphTransaction>> graphTransactionsMap = new TreeMap<>();

  @Override
  public void addFail(String transactionName, double relativeStartTimeMillis) {
    // failed transactions are ignored
  }

  @Override
  public synchronized void addPass(String transactionName, double relativeStartTimeMillis,
      double durationMillis) {
    List<GraphTransaction> transactions = getGraphTransactions(transactionName);
    if (transactions.size() == 10000000) {
      throw new RuntimeException("maximum of 10,000,000 transactions reached");
    }
    transactions.add(new GraphTransaction(relativeStartTimeMillis, durationMillis));
    Collections.sort(transactions);
    if (durationMillis > maxDurationMillis) {
      maxDurationMillis = durationMillis;
    }
  }

  private List<GraphTransaction> getGraphTransactions(String transactionName) {
    List<GraphTransaction> transactions = graphTransactionsMap.get(transactionName);
    if (transactions == null) {
      transactions = new ArrayList<>();
      graphTransactionsMap.put(transactionName, transactions);
    }
    return transactions;
  }

  public synchronized String getJson() {
    GraphInfo graphInfo = new GraphInfo();
    if (graphTransactionsMap.size() > 0) {
      double minRelativeStartTimeMillis = Double.MAX_VALUE;
      double maxRelativeEndTimeMillis = Double.MIN_VALUE;
      for (List<GraphTransaction> graphTransactions : graphTransactionsMap.values()) {
        double relativeStartTimeMillis = graphTransactions.get(0).relativeStartTimeMillis;
        if (relativeStartTimeMillis < minRelativeStartTimeMillis) {
          minRelativeStartTimeMillis = relativeStartTimeMillis;
        }
        GraphTransaction lastGraphTransaction =
            graphTransactions.get(graphTransactions.size() - 1);
        double relativeEndTimeMillis =
            lastGraphTransaction.relativeStartTimeMillis + lastGraphTransaction.durationMillis;
        if (relativeEndTimeMillis > maxRelativeEndTimeMillis) {
          maxRelativeEndTimeMillis = relativeEndTimeMillis;
        }
      }

      for (Entry<String, List<GraphTransaction>> entry : graphTransactionsMap.entrySet()) {
        GraphInfo.TransactionInfo transactionInfo =
            new GraphInfo.TransactionInfo(entry.getKey(), entry.getValue(),
                minRelativeStartTimeMillis, maxRelativeEndTimeMillis, maxDurationMillis);
        graphInfo.transactions.add(transactionInfo);
      }
    }

    return Util.throwUnchecked(() -> objectMapper.writeValueAsString(graphInfo));
  }
}