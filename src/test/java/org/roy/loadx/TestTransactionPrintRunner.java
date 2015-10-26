package org.roy.loadx;

import org.roy.loadx.transaction.TransactionAggregator;
import org.roy.loadx.transaction.TransactionPrintRunner;

public class TestTransactionPrintRunner implements TransactionPrintRunner {
	@Override
	public void run() {
		System.out.println("transaction print runner - run");
	}

	@Override
	public void print(TransactionAggregator transactionAggregator) {
		System.out.println("transaction print runner - print");
	}

	@Override
	public void end() {
		System.out.println("transaction print runner - end");
	}
}
