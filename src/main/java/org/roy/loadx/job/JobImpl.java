package org.roy.loadx.job;

import java.util.HashMap;
import java.util.Map;

import org.roy.loadx.api.Job;
import org.roy.loadx.api.JobInitializer;
import org.roy.loadx.api.Scenario;
import org.roy.loadx.api.ScenarioData;

public class JobImpl implements Job {
	private final Map<Object, ScenarioData> scenarioClassDataMap = new HashMap<>();

	private Class<Scenario> scenarioClass;
	private long defaultScenarioIterationCount = DEFAULT_SCENARIO_ITERATION_COUNT;
	private long defaultScenarioRunIterationCount = DEFAULT_SCENARIO_RUN_ITERATION_COUNT;
	private int defaultScenarioUserCount = DEFAULT_SCENARIO_USER_COUNT;
	private JobInitializer jobInitializer;

	@SuppressWarnings("unchecked")
	@Override
	public void setScenario(Object scenarioClass) {
		// Uses JDK internal class to allow Javascript job to pass scenario classes without .class suffix. Probably only works with Java 8
		jdk.internal.dynalink.beans.StaticClass staticClass = (jdk.internal.dynalink.beans.StaticClass) scenarioClass;
		this.scenarioClass = (Class<Scenario>) staticClass.getRepresentedClass();
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
	public ScenarioData getScenarioClassData(Class<Scenario> scenario) {
		String className = scenario.getName();
		ScenarioData scenarioData = scenarioClassDataMap.get(className);
		if (scenarioData == null) {
			scenarioData = new ScenarioDataImpl();
			scenarioClassDataMap.put(className, scenarioData);
		}
		return scenarioData;
	}

	@Override
	public void setJobInitializer(JobInitializer jobInitializer) {
		this.jobInitializer = jobInitializer;
	}

	public JobInitializer getJobInitializer() {
		return jobInitializer;
	}
}