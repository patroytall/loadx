package org.roy.loadx.priv.transaction.print;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.roy.loadx.priv.transaction.TransactionAggregator;
import org.roy.loadx.priv.transaction.TransactionData;
import org.roy.loadx.test.ManualTest;

import java.util.Collections;

@Category(ManualTest.class)
public class TransactionPrinterImplManualTest {
  private TransactionPrinterImpl sut;
  
  private TransactionData getTransactionData() {
    TransactionData transactionData = mock(TransactionData.class);
    when(transactionData.getAverageDurationMilli()).thenReturn(1d);
    when(transactionData.getMinDurationMilli()).thenReturn(1d);
    when(transactionData.getMaxDurationMilli()).thenReturn(1d);
    when(transactionData.getTransactionCount()).thenReturn(1l);
    return transactionData;
  }
  
  @Test
  public void print() {
    TransactionAggregator transactionAggregator = mock(TransactionAggregator.class);
    final String TRANSACTION_NAME = "name";
    TransactionData transactionData = getTransactionData();
    when(transactionAggregator.getTransactionData("name")).thenReturn(transactionData);
    when(transactionAggregator.getSortedTransactionNames()).thenReturn(Collections.singletonList(TRANSACTION_NAME));
    sut = new TransactionPrinterImpl(transactionAggregator);
    sut.print();
  }
}
