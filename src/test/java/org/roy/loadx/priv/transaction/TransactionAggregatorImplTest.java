package org.roy.loadx.priv.transaction;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.roy.loadx.priv.engine.TestTimeProvider;
import org.roy.loadx.priv.transaction.TransactionAggregatorImpl;

public class TransactionAggregatorImplTest {
	@Test
	public void getTransactionAverageDurationMilli() {
		final String TRANSACTION_NAME = "trans1";
		final long DURATION_MILLI = 1000;

		TransactionAggregatorImpl sut = new TransactionAggregatorImpl();
		sut.addPass(TRANSACTION_NAME, DURATION_MILLI);

		assertEquals(DURATION_MILLI, sut.getTransactionData(TRANSACTION_NAME).getAverageDurationMilli(), TestTimeProvider.TIME_DELTA);
	}
}
