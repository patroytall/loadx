package org.roy.loadx.transaction;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.roy.loadx.TestTimeProvider;
import org.roy.loadx.priv.transaction.TransactionData;

public class TransactionDataTest {
	@Test
	public void getAverageDurationMilli() {
		final long DURATION_MILLI = 1000;
		TransactionData sut = new TransactionData();
		assertEquals(DURATION_MILLI, sut.add(DURATION_MILLI).getAverageDurationMilli(), TestTimeProvider.TIME_DELTA);
	}
}
