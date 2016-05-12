package org.roy.loadx.job;

import org.roy.loadx.api.ExecutionData;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExecutionDataImpl implements ExecutionData {
	Map<String, Object> map = new ConcurrentHashMap<>();

	@Override
	public ExecutionData put(String name, String value) {
		map.put(name, value);
		return this;
	}

  @Override
  public Object getObject(String name) {
    Object object = map.get(name);
    if (object == null) {
      throw new RuntimeException("no execution data for name: " + name);
    }
    return object;
  }

  @Override
	public String getString(String name) {
    return (String)getObject(name);
	}
}