package org.roy.loadx.priv.transaction.grapher;

import java.util.List;

public class GraphInfo {
  public class TransactionInfo {
    public class Cordinate {
      public long x;
      public long y;
    }
    public String name;
    public List<Cordinate> coordinates;
  }
  public List<TransactionInfo> transactions;
}
