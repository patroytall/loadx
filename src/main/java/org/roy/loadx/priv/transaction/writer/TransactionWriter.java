package org.roy.loadx.priv.transaction.writer;

import org.roy.loadx.priv.transaction.TransactionListener;

public interface TransactionWriter extends TransactionListener {
  void terminate();
}
