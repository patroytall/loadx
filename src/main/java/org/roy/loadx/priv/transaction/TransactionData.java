package org.roy.loadx.priv.transaction;

import org.apache.commons.math3.stat.descriptive.SynchronizedDescriptiveStatistics;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Thread safe.
 */
public class TransactionData {
  private static final int WINDOW_SIZE = 2048;

  private final SynchronizedDescriptiveStatistics descriptiveStatistics;
  private final AtomicLong failCount;

  public TransactionData() {
    this(WINDOW_SIZE);
  }

  public TransactionData(TransactionData transactionData) {
    failCount = new AtomicLong(transactionData.failCount.get());
    descriptiveStatistics =
        new SynchronizedDescriptiveStatistics(transactionData.descriptiveStatistics);
  }

  TransactionData(int windowSize) {
    failCount = new AtomicLong();
    descriptiveStatistics = new SynchronizedDescriptiveStatistics(WINDOW_SIZE);
  }

  public TransactionData addPass(double durationMilli) {
    descriptiveStatistics.addValue(durationMilli);
    return this;
  }

  public void addFail() {
    failCount.incrementAndGet();
  }

  public double getAverageDurationMilli() {
    return descriptiveStatistics.getMean();
  }

  public double getMinDurationMilli() {
    return descriptiveStatistics.getMin();
  }

  public double getMaxDurationMilli() {
    return descriptiveStatistics.getMax();
  }

  public double get90PercentileEstimate() {
    return descriptiveStatistics.getPercentile(90);
  }

  public double getStandardDeviation() {
    return descriptiveStatistics.getStandardDeviation();
  }

  public long getPassCount() {
    return descriptiveStatistics.getN();
  }

  public long getFailCount() {
    return failCount.get();
  }
}