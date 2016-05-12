package org.roy.loadx.job;

import org.roy.loadx.api.ExecutionData;
import org.roy.loadx.api.Job;
import org.roy.loadx.api.JobInitializer;
import org.roy.loadx.api.JobScenario;
import org.roy.loadx.api.Scenario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobImpl implements Job {
  private final Map<Class<Scenario>, ExecutionData> scenarioClassDataMap = new HashMap<>();
  private final List<JobScenarioImpl> jobScenarios = new ArrayList<>();

  private long defaultScenarioIterationCount = DEFAULT_SCENARIO_ITERATION_COUNT;
  private long defaultScenarioRunIterationCount = DEFAULT_SCENARIO_RUN_ITERATION_COUNT;
  private int defaultScenarioUserCount = DEFAULT_SCENARIO_USER_COUNT;
  private JobInitializer jobInitializer;

  @SuppressWarnings("unchecked")
  @Override
  public JobScenario addScenario(Object scenarioClass) {
    Class<Scenario> javaScenarioClass =
        (Class<Scenario>) getClassFromJavascriptOrJavaClass(scenarioClass);
    JobScenarioImpl jobScenarioImpl = new JobScenarioImpl(javaScenarioClass);
    jobScenarios.add(jobScenarioImpl);
    return jobScenarioImpl;
  }

  @Override
  public ExecutionData getScenarioClassData(Object scenarioClass) {
    @SuppressWarnings("unchecked")
    Class<Scenario> javaScenarioClass =
        (Class<Scenario>) getClassFromJavascriptOrJavaClass(scenarioClass);

    ExecutionData scenarioClassData = scenarioClassDataMap.get(javaScenarioClass);
    if (scenarioClassData == null) {
      scenarioClassData = new ExecutionDataImpl();
      scenarioClassDataMap.put(javaScenarioClass, scenarioClassData);
    }
    return scenarioClassData;
  }

  public List<JobScenarioImpl> getJobScenarios() {
    return jobScenarios;
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

  private Class<?> getClassFromJavascriptOrJavaClass(Object clazz) {
    if (clazz instanceof jdk.internal.dynalink.beans.StaticClass) {
      // Uses JDK internal class to allow Javascript job to pass scenario classes without .class
      // suffix. Probably only works with Java 8
      jdk.internal.dynalink.beans.StaticClass staticClass =
          (jdk.internal.dynalink.beans.StaticClass) clazz;
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