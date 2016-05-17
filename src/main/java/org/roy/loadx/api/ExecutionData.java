package org.roy.loadx.api;

/**
 * Thread safe.
 */
public interface ExecutionData {
  ExecutionData put(Object key, Object value);

  /**
   * @throws RuntimeException if object is not found
   */
  Object getObject(Object key);

  /**
   * See {@link #getObject(Object)}
   */
  String getString(Object key);
  
  /**
   * See {@link #getObject(Object)}
   */
  Long getLong(Object key);
}