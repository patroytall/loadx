package org.roy.loadx.job;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.roy.loadx.api.ExecutionData;

public class ExecutionDataImpl implements ExecutionData {
	Map<String, String> map = new ConcurrentHashMap<>();

	@Override
	public ExecutionData put(String name, String value) {
		map.put(name, value);
		return this;
	}

	@Override
	public String getString(Object name) {
		String strName = name.toString();
		String str = (String) map.get(strName);
		if (str == null) {
			throw new RuntimeException("no execution data for name: " + strName);
		}
		return str;
	}
}