package org.roy.loadx.job;

import org.roy.loadx.api.ExecutionData;
import org.roy.loadx.api.Scenario;
import org.roy.loadx.api.TransactionRecorder;
import org.roy.loadx.transaction.TimeProvider;
import org.roy.loadx.transaction.TransactionAggregator;
import org.roy.loadx.transaction.TransactionRecorderImpl;

public class ScenarioRunner implements Runnable {
  private final Scenario scenario;
  private final long scenarioIterationCount;
  private final long scenarioRunIterationCount;
  private final ExecutionData scenarioData;
  private final ExecutionData scenarioClassData;
  private final ExecutionData jobData;
  private final TransactionAggregator transactionAggregator;
  private final TimeProvider timeProvider;

  public ScenarioRunner(Scenario scenario, long defaultScenarioIterationCount,
      long defaultScenarioRunIterationCount, ExecutionData scenarioData,
      ExecutionData scenarioClassData, ExecutionData jobData,
      TransactionAggregator transactionAggregator, TimeProvider timeProvider) {
    this.scenario = scenario;
    this.scenarioIterationCount = defaultScenarioIterationCount;
    this.scenarioRunIterationCount = defaultScenarioRunIterationCount;
    this.scenarioData = scenarioData;
    this.scenarioClassData = scenarioClassData;
    this.jobData = jobData;
    this.transactionAggregator = transactionAggregator;
    this.timeProvider = timeProvider;
  }

  @Override
  public void run() {
    TransactionRecorder transactionRecorder =
        new TransactionRecorderImpl(transactionAggregator, timeProvider);

    scenario.initializeScenarioThread(scenarioData, scenarioClassData, jobData,
        transactionRecorder);
    
    for (long i = 0; i < scenarioIterationCount; ++i) {
      scenario.start();
      for (long j = 0; j < scenarioRunIterationCount; ++j) {
        scenario.run();
      }
      scenario.end();
    }
    
    scenario.terminateScenarioThread(scenarioData, scenarioClassData, jobData);
  }
}