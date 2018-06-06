package org.roy.loadx.priv.transaction;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.roy.loadx.priv.engine.TestTimeProvider;
import org.roy.loadx.priv.transaction.TransactionData;

public class TransactionDataTest {
  @Test
  public void getAverageDurationMilli() {
    final long DURATION_MILLI = 1000;
    TransactionData sut = new TransactionData();
    assertEquals(DURATION_MILLI, sut.addPass(DURATION_MILLI).getAverageDurationMilli(),
        TestTimeProvider.TIME_DELTA);
  }

//  @Test
//  public void getMaxOutsideWindow() {
//    final double MAX = 3;
//    final int WINDOW_SIZE = 10;
//    TransactionData sut = new TransactionData(WINDOW_SIZE);
//    sut.addPass(MAX);
//    for (int i = 0; i < WINDOW_SIZE; ++i) {
//      sut.addPass(MAX - 1);
//    }
//    assertEquals(MAX, sut.getMaxDurationMilli(), TestTimeProvider.TIME_DELTA);
//  }

//  @Test
//  public void get90PercentileEstimateIsWithinPoint2() {
//    final int WINDOW_SIZE = 1000;
//    TransactionData sut = new TransactionData(WINDOW_SIZE);
//    for (int i = 1; i <= WINDOW_SIZE; ++i) {
//      sut.addPass(i);
//    }
//    assertEquals(WINDOW_SIZE - (WINDOW_SIZE / 10 - 1), sut.get90PercentileEstimate(), 0.2);
//  }
}
