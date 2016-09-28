package org.roy.loadx.priv.job;

import org.roy.loadx.priv.engine.TimeProvider;
import org.roy.loadx.priv.transaction.TransactionAggregatorImpl;
import org.roy.loadx.priv.transaction.TransactionRecorderImpl;
import org.roy.loadx.pub.api.ExecutionData;
import org.roy.loadx.pub.api.JobInitializer;
import org.roy.loadx.pub.api.Scenario;
import org.roy.loadx.pub.api.ScenarioClassInitializer;

public class ScenarioRunner implements Runnable {
  private final Scenario scenario;
  private final long scenarioIterationCount;
  private final long scenarioRunIterationCount;
  private final ExecutionData scenarioData;
  private final ExecutionData scenarioClassData;
  private final ExecutionData jobData;
  private final TransactionAggregatorImpl transactionAggregator;
  private final TimeProvider timeProvider;
  private ScenarioClassInitializer scenarioClassInitializer;
  private JobInitializer jobInitializer;

  public ScenarioRunner(Scenario scenario, long defaultScenarioIterationCount,
      long defaultScenarioRunIterationCount, ExecutionData scenarioData,
      ExecutionData scenarioClassData, ExecutionData jobData,
      ScenarioClassInitializer scenarioClassInitializer, JobInitializer jobInitializer,
      TransactionAggregatorImpl transactionAggregator, TimeProvider timeProvider) {
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
    TransactionRecorderImpl transactionRecorderImpl =
        new TransactionRecorderImpl(transactionAggregator, timeProvider);

    scenario.initializeScenarioThread(scenarioData, scenarioClassData, jobData,
        scenarioClassInitializer, jobInitializer, transactionRecorderImpl);
    
    try {
      for (long i = 0; i < scenarioIterationCount; ++i) {
        scenario.start();
        for (long j = 0; j < scenarioRunIterationCount; ++j) {
          scenario.run();
        }
        scenario.end();
      }
    } catch (Throwable t) {
      transactionRecorderImpl.abort();
      t.printStackTrace();
    }
    
    scenario.terminateScenarioThread();
  }
}