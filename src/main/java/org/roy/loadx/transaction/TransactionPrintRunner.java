package org.roy.loadx.transaction;

public interface TransactionPrintRunner extends Runnable {
	void print(TransactionAggregator transactionAggregator);

	void end();
}
