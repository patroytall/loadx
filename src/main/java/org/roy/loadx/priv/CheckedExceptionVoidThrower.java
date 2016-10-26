package org.roy.loadx.priv;

@FunctionalInterface
public interface CheckedExceptionVoidThrower {
  void process() throws Exception;
}