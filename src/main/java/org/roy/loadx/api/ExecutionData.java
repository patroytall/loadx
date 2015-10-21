package org.roy.loadx.api;

public interface ExecutionData {
	ExecutionData put(String name, String value);

	/**
	 * @throws RuntimeException
	 *             if name is not found
	 */
	String getString(Object name);
}
