package org.roy.loadx.priv.transaction;

/**
 * Thread safe.
 */
public class TransactionData {
	private long transactionCount = 0;
	private double averageDurationMilli = 0;
	private double minDurationMilli = Double.MAX_VALUE;
	private double maxDurationMilli = Double.MIN_VALUE;

	public TransactionData() {
	}

	public TransactionData(TransactionData transactionData) {
		transactionCount = transactionData.transactionCount;
		averageDurationMilli = transactionData.averageDurationMilli;
		minDurationMilli = transactionData.minDurationMilli;
    maxDurationMilli = transactionData.maxDurationMilli;
	}

	public synchronized TransactionData add(double durationMilli) {
		long previousTransactionCount = transactionCount;
		transactionCount++;
		averageDurationMilli = (averageDurationMilli * previousTransactionCount + durationMilli) / transactionCount;
		if (durationMilli < minDurationMilli) {
		  minDurationMilli = durationMilli;
		}
    if (durationMilli > maxDurationMilli) {
      maxDurationMilli = durationMilli;
    }
		return this;
	}

	public double getAverageDurationMilli() {
		return averageDurationMilli;
	}

	public double getMinDurationMilli() {
	  return minDurationMilli;
	}
	
  public double getMaxDurationMilli() {
    return maxDurationMilli;
  }
  
	public long getTransactionCount() {
		return transactionCount;
	}
}