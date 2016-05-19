package org.roy.loadx.priv.job;

import org.roy.loadx.priv.transaction.TimeProvider;
import org.roy.loadx.priv.transaction.TransactionAggregator;
import org.roy.loadx.priv.transaction.TransactionRecorderImpl;
import org.roy.loadx.pub.api.ExecutionData;
import org.roy.loadx.pub.api.JobInitializer;
import org.roy.loadx.pub.api.Scenario;
import org.roy.loadx.pub.api.ScenarioClassInitializer;
import org.roy.loadx.pub.api.TransactionRecorder;

public class ScenarioRunner implements Runnable {
  private final Scenario scenario;
  private final long scenarioIterationCount;
  private final long scenarioRunIterationCount;
  private final ExecutionData scenarioData;
  private final ExecutionData scenarioClassData;
  private final ExecutionData jobData;
  private final TransactionAggregator transactionAggregator;
  private final TimeProvider timeProvider;
  private ScenarioClassInitializer scenarioClassInitializer;
  private JobInitializer jobInitializer;

  public ScenarioRunner(Scenario scenario, long defaultScenarioIterationCount,
      long defaultScenarioRunIterationCount, ExecutionData scenarioData,
      ExecutionData scenarioClassData, ExecutionData jobData,
      ScenarioClassInitializer scenarioClassInitializer, JobInitializer jobInitializer,
      TransactionAggregator transactionAggregator, TimeProvider timeProvider) {
    this.scenario = scenario;
    this.scenarioIterationCount = defaultScenarioIterationCount;
    this.scenarioRunIterationCount = defaultScenarioRunIterationCount;
    this.scenarioData = scenarioData;
    this.scenarioClassData = scenarioClassData;
    this.jobData = jobData;
    this.scenarioClassInitializer = scenarioClassInitializer;
    this.jobInitializer = jobInitializer;
    this.transactionAggregator = transactionAggregator;
    this.timeProvider = timeProvider;
  }

  @Override
  public void run() {
    TransactionRecorder transactionRecorder =
        new TransactionRecorderImpl(transactionAggregator, timeProvider);

    scenario.initializeScenarioThread(scenarioData, scenarioClassData, jobData,
        scenarioClassInitializer, jobInitializer, transactionRecorder);
    
    for (long i = 0; i < scenarioIterationCount; ++i) {
      scenario.start();
      for (long j = 0; j < scenarioRunIterationCount; ++j) {
        scenario.run();
      }
      scenario.end();
    }
    
    scenario.terminateScenarioThread();
  }
}