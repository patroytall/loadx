package org.roy.loadx.transaction;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.roy.loadx.TestTimeProvider;

public class TransactionAggregatorTest {
	@Test
	public void getTransactionAverageDurationMilli() {
		final String TRANSACTION_NAME = "trans1";
		final long DURATION_MILLI = 1000;

		TransactionAggregator sut = new TransactionAggregator();
		sut.addTransaction(TRANSACTION_NAME, DURATION_MILLI);

		assertEquals(DURATION_MILLI, sut.getTransactionData(TRANSACTION_NAME).getAverageDurationMilli(), TestTimeProvider.TIME_DELTA);
	}
}
