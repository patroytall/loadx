package org.roy.loadx.transaction;

public class Transaction {
	private double durationMilli;

	public Transaction(double durationMilli) {
		this.durationMilli = durationMilli;
	}

	public double getDurationMilli() {
		return durationMilli;
	}
}
