package org.roy.loadx.priv;

@FunctionalInterface
public interface CheckedExceptionThrower<T> {
  T get() throws Exception;
}