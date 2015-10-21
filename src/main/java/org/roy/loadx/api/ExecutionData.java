package org.roy.loadx.api;

public interface ExecutionData {
	ExecutionData put(String name, String value);

	String getString(Object name);
}
