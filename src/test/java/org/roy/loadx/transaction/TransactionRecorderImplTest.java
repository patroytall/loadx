package org.roy.loadx.transaction;

import org.junit.Test;
import org.roy.loadx.TestTimeProvider;
import org.roy.loadx.priv.transaction.TimeProvider;
import org.roy.loadx.priv.transaction.TransactionAggregator;
import org.roy.loadx.priv.transaction.TransactionRecorderImpl;

import mockit.Expectations;
import mockit.Mocked;

public class TransactionRecorderImplTest {
	private final TimeProvider testTimeProvider = new TestTimeProvider();

	@Test
	public void startEndForOneNano(@Mocked TransactionAggregator transactionAggregator) {
		final String TRANSACTION_NAME = "trans1";
		final double EXPECTED_DURATION_MILLI = 1e-6;
		new Expectations() {
			{
				transactionAggregator.addTransaction(TRANSACTION_NAME, EXPECTED_DURATION_MILLI);
			}
		};

		TransactionRecorderImpl sut = new TransactionRecorderImpl(transactionAggregator, testTimeProvider);

		sut.start(TRANSACTION_NAME);
		testTimeProvider.sleep(0, (int) (EXPECTED_DURATION_MILLI * 1e6));
		sut.end();
	}
}