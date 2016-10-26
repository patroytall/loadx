package org.roy.loadx.priv.transaction.grapher;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.roy.loadx.priv.Util;
import org.roy.loadx.priv.transaction.TransactionListener;

public class TransactionGrapher implements TransactionListener {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  String buf = "";

  @Override
  public void addFail(String transactionName) {
  }

  @Override
  public void addPass(String transactionName, double durationMilli) {
    buf += transactionName;
  }

  public String getJson() {
    GraphInfo grapherInfo = new GraphInfo();
    return Util.throwUnchecked(() -> objectMapper.writeValueAsString(grapherInfo));
  }
}
