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
  private final ExecutionData scenarioClassData;
  private final ExecutionData scenarioObjectData;
  private final TransactionAggregator transactionAggregator;
  private final TimeProvider timeProvider;

  public ScenarioRunner(Scenario scenario, long defaultScenarioIterationCount,
      long defaultScenarioRunIterationCount, ExecutionData scenarioClassData,
      ExecutionData scenarioObjectData, TransactionAggregator transactionAggregator,
      TimeProvider timeProvider) {
    this.scenario = scenario;
    this.scenarioIterationCount = defaultScenarioIterationCount;
    this.scenarioRunIterationCount = defaultScenarioRunIterationCount;
    this.scenarioClassData = scenarioClassData;
    this.scenarioObjectData = scenarioObjectData;
    this.transactionAggregator = transactionAggregator;
    this.timeProvider = timeProvider;
  }

  @Override
  public void run() {
    TransactionRecorder transactionRecorder =
        new TransactionRecorderImpl(transactionAggregator, timeProvider);
    scenario.initializeObject(scenarioClassData, scenarioObjectData, transactionRecorder);
    for (long i = 0; i < scenarioIterationCount; ++i) {
      scenario.start();
      for (long j = 0; j < scenarioRunIterationCount; ++j) {
        scenario.run();
      }
      scenario.end();
    }
    scenario.terminateObject();
  }
}
