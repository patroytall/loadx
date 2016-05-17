package org.roy.loadx.job;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ExecutionDataImplTest {
  private static final String KEY = "key";
  private ExecutionDataImpl sut = new ExecutionDataImpl();

  @Test
  public void getLong() {
    sut.put(KEY, 1);
    assertEquals(1, sut.getLong(KEY).longValue());
  }

  @Test(expected = RuntimeException.class)
  public void notFoundThrows() {
    sut.getObject(KEY);
  }
}
