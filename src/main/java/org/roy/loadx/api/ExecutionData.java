package org.roy.loadx.api;

public interface ExecutionData {
  ExecutionData put(String name, String value);

  /**
   * @throws RuntimeException if name is not found
   */
  Object getObject(String name);

  String getString(String name);
}
