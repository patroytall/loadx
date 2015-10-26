package org.roy.loadx.transaction;

public class TransactionPrintRunnerImpl implements TransactionPrintRunner {
	private static final long PRINT_INTERVAL_TIME_SECONDS = 3;

	private TransactionPrinter transactionPrinter;

	private boolean print = false;
	private boolean done = false;

	@Override
	public void run() {
		while (!done) {
			if (print) {
				printTransactions();
			}
			try {
				Thread.sleep(PRINT_INTERVAL_TIME_SECONDS * 1000);
			} catch (InterruptedException e) {
				if (!done) {
					throw new RuntimeException("invalid not done interruption");
				}
			}
		}
		printTransactions();
	}

	private void printTransactions() {
		transactionPrinter.print();
	}

	@Override
	public void print(TransactionAggregator transactionAggregator) {
		transactionPrinter = new TransactionPrinter(transactionAggregator);
		print = true;
	}

	@Override
	public void end() {
		done = true;
		Thread.currentThread().interrupt();
	}
}
