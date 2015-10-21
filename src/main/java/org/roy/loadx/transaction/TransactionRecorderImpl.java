package org.roy.loadx.transaction;

import org.roy.loadx.api.TransactionRecorder;

public class TransactionRecorderImpl implements TransactionRecorder {
	private final TransactionAggregator transactionAggregator;
	private final TimeProvider timeProvider;

	private long startTimeNano;
	private String name;

	public TransactionRecorderImpl(TransactionAggregator transactionAggregator, TimeProvider timeProvider) {
		this.transactionAggregator = transactionAggregator;
		this.timeProvider = timeProvider;
	}

	@Override
	public void start(String name) {
		startTimeNano = timeProvider.nanoTime();
	}

	@Override
	public void end() {
		long endTimeNano = timeProvider.nanoTime();
		double durationMilli = (endTimeNano - startTimeNano) / 1000000;
		transactionAggregator.addTransaction(name, durationMilli);
	}
}