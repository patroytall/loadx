package org.roy.loadx.priv.transaction;

import org.roy.loadx.priv.engine.TimeProvider;
import org.roy.loadx.pub.api.TransactionRecorder;

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
		this.name = name;
		startTimeNano = timeProvider.nanoTime();
	}

	@Override
	public void end() {
		long endTimeNano = timeProvider.nanoTime();
		double durationMilli = (endTimeNano - startTimeNano) / 1e6;
		transactionAggregator.addTransaction(name, durationMilli);
	}
}