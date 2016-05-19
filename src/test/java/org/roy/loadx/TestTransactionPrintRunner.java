package org.roy.loadx;

import org.roy.loadx.priv.transaction.print.TransactionPrintRunner;

public class TestTransactionPrintRunner implements TransactionPrintRunner {
	@Override
	public void start() {
		System.out.println("test transaction print runner - start");
	}

	@Override
	public void done() {
		System.out.println("test transaction print runner - done");
	}
}