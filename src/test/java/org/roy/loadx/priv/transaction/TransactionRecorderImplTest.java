package org.roy.loadx.priv.transaction;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.roy.loadx.priv.engine.TestTimeProvider;
import org.roy.loadx.priv.engine.TimeProvider;

public class TransactionRecorderImplTest {
  private static final String TRANSACTION_NAME = "transactionName";
	
  private final TimeProvider testTimeProvider = new TestTimeProvider();

  private final TransactionAggregator transactionAggregator = mock(TransactionAggregator.class);
  private final TransactionRecorderImpl sut = new TransactionRecorderImpl(transactionAggregator, testTimeProvider);

	@Test
	public void startEndForOneNano() {
		final double EXPECTED_DURATION_MILLI = 1e-6;
		sut.start(TRANSACTION_NAME);
		testTimeProvider.sleep(0, (int) (EXPECTED_DURATION_MILLI * 1e6));
		sut.end();
		
    verify(transactionAggregator).addPass(TRANSACTION_NAME, EXPECTED_DURATION_MILLI);
	}
	
	@Test
	public void abort() {
    sut.start(TRANSACTION_NAME);
    sut.abort();
    
    verify(transactionAggregator).addFail(TRANSACTION_NAME);
	}
}