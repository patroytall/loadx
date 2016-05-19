package org.roy.loadx.priv.job;

import org.roy.loadx.pub.api.ExecutionData;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread safe.
 */
public class ExecutionDataImpl implements ExecutionData {
  Map<Object, Object> map = new ConcurrentHashMap<>();

  @Override
  public ExecutionData put(Object key, Object value) {
    map.put(key, value);
    return this;
  }

  @Override
  public Object getObject(Object key) {
    Object object = map.get(key);
    if (object == null) {
      throw new RuntimeException("no execution data for key: " + key);
    }
    return object;
  }

  @Override
  public String getString(Object key) {
    return (String) getObject(key);
  }

  @Override
  public Long getLong(Object key) {
    Object object = getObject(key);
    if (object instanceof Integer) {
      return ((Integer) object).longValue();
    } else {
      return (Long) object;
    }
  }

  @Override
  public String toString() {
    return map.toString();
  }
}