package org.roy.loadx.priv.transaction;

import org.apache.commons.math3.stat.descriptive.SynchronizedDescriptiveStatistics;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Thread safe.
 * getPassCount is not fully thread safe after WINDOW_SIZE transactions have been recorded. It can be off by 1.
 */
public class TransactionData {
  private static final int WINDOW_SIZE = 65536;

  private final SynchronizedDescriptiveStatistics descriptiveStatistics;
  private final AtomicLong failCount;
  private final AtomicLong passCount;

  public TransactionData() {
    failCount = new AtomicLong();
    passCount = new AtomicLong();
    descriptiveStatistics = new SynchronizedDescriptiveStatistics(WINDOW_SIZE);
  }

  public TransactionData(TransactionData transactionData) {
    failCount = new AtomicLong(transactionData.failCount.get());
    passCount = new AtomicLong(transactionData.passCount.get());
    descriptiveStatistics =
        new SynchronizedDescriptiveStatistics(transactionData.descriptiveStatistics);
  }

  public TransactionData addPass(double durationMilli) {
    descriptiveStatistics.addValue(durationMilli);
    passCount.incrementAndGet();
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
    return descriptiveStatistics.getN() < WINDOW_SIZE ? descriptiveStatistics.getN() : passCount.longValue();
  }

  public long getFailCount() {
    return failCount.get();
  }
}