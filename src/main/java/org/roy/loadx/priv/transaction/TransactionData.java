package org.roy.loadx.priv.transaction;

/**
 * Thread safe.
 */
public class TransactionData {
  private long passCount = 0;
  private long failCount = 0;
  private double averageDurationMilli = 0;
  private double minDurationMilli = Double.MAX_VALUE;
  private double maxDurationMilli = Double.MIN_VALUE;

  public TransactionData() {
  }
  
  public TransactionData(TransactionData transactionData) {
    passCount = transactionData.passCount;
    failCount = transactionData.failCount;
    averageDurationMilli = transactionData.averageDurationMilli;
    minDurationMilli = transactionData.minDurationMilli;
    maxDurationMilli = transactionData.maxDurationMilli;
  }

  public synchronized TransactionData addPass(double durationMilli) {
    long previousPassedCount = passCount;
    passCount++;
    averageDurationMilli =
        (averageDurationMilli * previousPassedCount + durationMilli) / passCount;
    if (durationMilli < minDurationMilli) {
      minDurationMilli = durationMilli;
    }
    if (durationMilli > maxDurationMilli) {
      maxDurationMilli = durationMilli;
    }
    return this;
  }

  public synchronized void addFail() {
    failCount++;
  }
  
  public synchronized double getAverageDurationMilli() {
    return averageDurationMilli;
  }

  public synchronized double getMinDurationMilli() {
    return minDurationMilli;
  }

  public synchronized double getMaxDurationMilli() {
    return maxDurationMilli;
  }

  public synchronized long getPassCount() {
    return passCount;
  }
  
  public synchronized long getFailCount() {
    return failCount;
  }
}