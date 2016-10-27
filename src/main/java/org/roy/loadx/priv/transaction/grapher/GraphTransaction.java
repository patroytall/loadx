package org.roy.loadx.priv.transaction.grapher;

/**
 * Immutable
 */
public class GraphTransaction implements Comparable<GraphTransaction> {
  public final double durationMillis;
  public final double relativeStartTimeMillis;

  public GraphTransaction(double relativeStartTimeMillis, double durationMillis) {
    this.relativeStartTimeMillis = relativeStartTimeMillis;
    this.durationMillis = durationMillis;
  }

  @Override
  public int compareTo(GraphTransaction graphTransaction) {
    if (relativeStartTimeMillis == graphTransaction.relativeStartTimeMillis) {
      return 0;
    } else {
      return relativeStartTimeMillis < graphTransaction.relativeStartTimeMillis ? -1 : 1;
    }
  }
}