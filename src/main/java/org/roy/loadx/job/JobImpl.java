package org.roy.loadx.job;

import java.util.HashMap;
import java.util.Map;

import org.roy.loadx.api.ExecutionData;
import org.roy.loadx.api.Job;
import org.roy.loadx.api.JobInitializer;
import org.roy.loadx.api.Scenario;

public class JobImpl implements Job {
	private final Map<Object, ExecutionData> scenarioClassDataMap = new HashMap<>();

	private Class<Scenario> scenarioClass;
	private long defaultScenarioIterationCount = DEFAULT_SCENARIO_ITERATION_COUNT;
	private long defaultScenarioRunIterationCount = DEFAULT_SCENARIO_RUN_ITERATION_COUNT;
	private int defaultScenarioUserCount = DEFAULT_SCENARIO_USER_COUNT;
	private JobInitializer jobInitializer;

	@SuppressWarnings("unchecked")
	@Override
	public Object addScenario(Object scenarioClass) {
		if (this.scenarioClass != null) {
			throw new RuntimeException("only one scenario currently supported");
		}
		this.scenarioClass = (Class<Scenario>) getClassFromJavascriptOrJavaClass(scenarioClass);
		return scenarioClass;
	}

	@Override
	public Class<Scenario> getScenarioClass() {
		return scenarioClass;
	}

	@Override
	public long getDefaultScenarioIterationCount() {
		return defaultScenarioIterationCount;
	}

	@Override
	public void setDefaultScenarioIterationCount(long count) {
		defaultScenarioIterationCount = count;
	}

	@Override
	public long getDefaultScenarioRunIterationCount() {
		return defaultScenarioRunIterationCount;
	}

	@Override
	public void setDefaultScenarioRunIterationCount(int count) {
		defaultScenarioRunIterationCount = count;
	}

	@Override
	public void setDefaultScenarioUserCount(int count) {
		defaultScenarioUserCount = count;
	}

	@Override
	public int getDefaultScenarioUserCount() {
		return defaultScenarioUserCount;
	}

	@Override
	public ExecutionData getScenarioClassData(Object scenarioClass) {
		String className = getClassFromJavascriptOrJavaClass(scenarioClass).getName();
		ExecutionData scenarioClassData = scenarioClassDataMap.get(className);
		if (scenarioClassData == null) {
			scenarioClassData = new ExecutionDataImpl();
			scenarioClassDataMap.put(className, scenarioClassData);
		}
		return scenarioClassData;
	}

	private Class<?> getClassFromJavascriptOrJavaClass(Object clazz) {
		if (clazz instanceof jdk.internal.dynalink.beans.StaticClass) {
			// Uses JDK internal class to allow Javascript job to pass scenario classes without .class suffix. Probably only works with Java 8
			jdk.internal.dynalink.beans.StaticClass staticClass = (jdk.internal.dynalink.beans.StaticClass) clazz;
			return staticClass.getRepresentedClass();
		} else {
			return (Class<?>) clazz;
		}
	}

	@Override
	public void setJobInitializer(JobInitializer jobInitializer) {
		this.jobInitializer = jobInitializer;
	}

	public JobInitializer getJobInitializer() {
		return jobInitializer;
	}
}