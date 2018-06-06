package org.roy.loadx.priv.transaction;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.SynchronizedDescriptiveStatistics;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Thread safe.
 * 90 percentile and standard deviation are only accurate within the WINDOW_SIZE of 16384 entries.
 */
public class TransactionData {
  private static final int WINDOW_SIZE = 16384;

  private final long windowSize;

  private final DescriptiveStatistics descriptiveStatistics;
  private final AtomicLong failCount;
  private long passCount;
  private double min;
  private double max;
  private double total;

  TransactionData(int windowSize) {
    this.windowSize = windowSize;
    failCount = new AtomicLong();
    min = Double.NaN;
    max = Double.NaN;
    descriptiveStatistics = new SynchronizedDescriptiveStatistics(windowSize);
  }

  public TransactionData() {
    this(WINDOW_SIZE);
  }

  public TransactionData(TransactionData transactionData) {
    synchronized (transactionData) {
      failCount = new AtomicLong(transactionData.failCount.get());
      passCount = transactionData.passCount;
      min = transactionData.min;
      max = transactionData.max;
      total = transactionData.total;
      windowSize = transactionData.windowSize;
      descriptiveStatistics = new DescriptiveStatistics(transactionData.descriptiveStatistics);
    }
  }

  public synchronized TransactionData addPass(double durationMilli) {
    descriptiveStatistics.addValue(durationMilli);
    passCount++;
    if (Double.isNaN(max) || durationMilli > max) {
      max = durationMilli;
    }
    if (Double.isNaN(min) || durationMilli < min) {
      min = durationMilli;
    }
    total += durationMilli;
    return this;
  }

  public void addFail() {
    failCount.incrementAndGet();
  }

  public synchronized double getAverageDurationMilli() {
    return total / passCount;
  }

  public synchronized double getMinDurationMilli() {
    return min;
  }

  public synchronized double getMaxDurationMilli() {
    return max;
  }

  public synchronized double get90PercentileEstimate() {
    return descriptiveStatistics.getPercentile(90);
  }

  public synchronized double getStandardDeviation() {
    return descriptiveStatistics.getStandardDeviation();
  }

  public synchronized long getPassCount() {
    return passCount;
  }

  public long getFailCount() {
    return failCount.get();
  }
}