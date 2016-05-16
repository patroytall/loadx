package org.roy.loadx;

import org.roy.loadx.transaction.TransactionPrintRunner;

public class TestTransactionPrintRunner implements TransactionPrintRunner {
	@Override
	public void run() {
		System.out.println("transaction print runner - run");
	}

	@Override
	public void stop() {
		System.out.println("transaction print runner - stop");
	}
}
