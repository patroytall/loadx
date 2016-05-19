package org.roy.loadx.priv.transaction;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.roy.loadx.priv.engine.TestTimeProvider;
import org.roy.loadx.priv.engine.TimeProvider;

public class TransactionRecorderImplTest {
	private final TimeProvider testTimeProvider = new TestTimeProvider();

	@Test
	public void startEndForOneNano() {
	  TransactionAggregator transactionAggregator = mock(TransactionAggregator.class);
		final String TRANSACTION_NAME = "trans1";
		final double EXPECTED_DURATION_MILLI = 1e-6;

		TransactionRecorderImpl sut = new TransactionRecorderImpl(transactionAggregator, testTimeProvider);

		sut.start(TRANSACTION_NAME);
		testTimeProvider.sleep(0, (int) (EXPECTED_DURATION_MILLI * 1e6));
		sut.end();
		
    verify(transactionAggregator).addTransaction(TRANSACTION_NAME, EXPECTED_DURATION_MILLI);
	}
}