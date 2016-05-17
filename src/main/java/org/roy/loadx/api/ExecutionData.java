package org.roy.loadx.api;

public interface ExecutionData {
  ExecutionData put(Object key, Object value);

  /**
   * @throws RuntimeException if object is not found
   */
  Object getObject(Object key);

  String getString(Object key);
  
  Long getLong(Object key);
}