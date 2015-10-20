package org.roy.loadx.job;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.roy.loadx.api.ScenarioData;

public class ScenarioDataImpl implements ScenarioData {
	Map<String, String> map = new ConcurrentHashMap<>();

	@Override
	public void put(String name, String value) {
		map.put(name, value);
	}

	@Override
	public String getString(String name) {
		return (String) map.get(name);
	}
}