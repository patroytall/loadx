package org.roy.loadx.priv.transaction.grapher;

import java.util.ArrayList;
import java.util.List;

public class GraphInfo {
  public static class TransactionInfo {
    public class Point {
      public long x;
      public long y;

      public Point(long x, long y) {
        this.x = x;
        this.y = y;
      }
    }

    public String name;
    public List<Point> points = new ArrayList<>();

    public TransactionInfo(String transactionName, List<GraphTransaction> graphTransactions,
        double minRelativeStartTimeMillis, double maxRelativeEndTimeMillis,
        double maxDurationMillis) {
      this.name = transactionName;
      if (graphTransactions.size() > 1) {
        addGraphTransactionsToPointList(graphTransactions, minRelativeStartTimeMillis,
            maxRelativeEndTimeMillis, maxDurationMillis);
      }
    }

    /**
     * @param graphTransactions size > 1
     */
    private void addGraphTransactionsToPointList(List<GraphTransaction> graphTransactions,
        double minRelativeStartTimeMillis, double maxRelativeEndTimeMillis,
        double maxDurationMillis) {

      long intervalCount = Math.min(99, graphTransactions.size());
      double totalRange = maxRelativeEndTimeMillis - minRelativeStartTimeMillis;
      double intervalRange = totalRange / intervalCount;
      int gtIndex = 0;
      for (int i = 0; i < intervalCount && gtIndex < graphTransactions.size(); i++) {
        double rangeStart = minRelativeStartTimeMillis + (intervalRange * i);
        double rangeEnd = i == intervalCount - 1 ? Double.MAX_VALUE : rangeStart + intervalRange;

        boolean inRange = true;
        while (gtIndex < graphTransactions.size() && inRange) {
          GraphTransaction graphTransaction = graphTransactions.get(gtIndex);
          inRange =
              graphTransaction.relativeStartTimeMillis >= rangeStart
                  && graphTransaction.relativeStartTimeMillis < rangeEnd;
          if (inRange) {
            long y = Math.round(graphTransaction.durationMillis / maxDurationMillis * 100);
            points.add(new Point(i, y));
            gtIndex++;
          }
        }
      }
    }
  }

  public List<TransactionInfo> transactions = new ArrayList<>();
}