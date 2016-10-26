package org.roy.loadx.priv.transaction.grapher;

import java.util.ArrayList;
import java.util.List;

public class GraphInfo {
  public static class TransactionInfo {
    public class Cordinate {
      public long x;
      public long y;
      public Cordinate(long x, long y) {
        this.x = x;
        this.y = y;
      }
    }
    
    public String name;
    public List<Cordinate> coordinates = new ArrayList<>();

    public TransactionInfo(String transactionName, List<Double> durationsMilli) {
      this.name = transactionName;
      for (Double durationMilli : durationsMilli) {
        coordinates.add(new Cordinate(0, durationMilli.longValue()));
      }
    }
  }
  public List<TransactionInfo> transactions = new ArrayList<>();
}
