package org.roy.loadx.priv.job;

import org.roy.loadx.priv.engine.time.TimeHandler;
import org.roy.loadx.priv.transaction.TransactionListener;
import org.roy.loadx.priv.transaction.TransactionRecorderImpl;
import org.roy.loadx.pub.api.ExecutionData;
import org.roy.loadx.pub.api.JobInitializer;
import org.roy.loadx.pub.api.Scenario;
import org.roy.loadx.pub.api.ScenarioClassInitializer;

import java.util.List;

public class ScenarioRunner implements Runnable {
  private final Scenario scenario;
  private final long scenarioIterationCount;
  private final long scenarioRunIterationCount;
  private final ExecutionData scenarioData;
  private final ExecutionData scenarioClassData;
  private final ExecutionData jobData;
  private final ScenarioClassInitializer scenarioClassInitializer;
  private final JobInitializer jobInitializer;
  private final TransactionRecorderImpl transactionRecorderImpl;

  public ScenarioRunner(Scenario scenario, long defaultScenarioIterationCount,
      long defaultScenarioRunIterationCount, ExecutionData scenarioData,
      ExecutionData scenarioClassData, ExecutionData jobData,
      ScenarioClassInitializer scenarioClassInitializer, JobInitializer jobInitializer,
      List<TransactionListener> transactionListeners, TimeHandler timeHandler) {
    this.scenario = scenario;
    this.scenarioIterationCount = defaultScenarioIterationCount;
    this.scenarioRunIterationCount = defaultScenarioRunIterationCount;
    this.scenarioData = scenarioData;
    this.scenarioClassData = scenarioClassData;
    this.jobData = jobData;
    this.scenarioClassInitializer = scenarioClassInitializer;
    this.jobInitializer = jobInitializer;
    transactionRecorderImpl = new TransactionRecorderImpl(transactionListeners, timeHandler);
  }

  private void handleStepFailure(String step, Exception e) {
    System.err.println("scenario step " + step + " failed");
    if (transactionRecorderImpl.transactionRunning()) {
      System.err.println("transaction " + transactionRecorderImpl.getTransactionName()
          + " aborted");
    }
    e.printStackTrace();
    System.err.flush();

    transactionRecorderImpl.abort();
  }

  private boolean runStartStep() {
    try {
      scenario.start();
    } catch (Exception e) {
      handleStepFailure("start", e);
      return false;
    }
    return true;
  }

  private void runRunStep() {
    try {
      scenario.run();
    } catch (Exception e) {
      handleStepFailure("run", e);
    }
  }

  private void runEndStep() {
    try {
      scenario.end();
    } catch (Exception e) {
      handleStepFailure("end", e);
    }
  }

  private void runScenario() {
    for (long i = 0; i < scenarioIterationCount; ++i) {
      if (runStartStep()) {
        for (long j = 0; j < scenarioRunIterationCount; ++j) {
          runRunStep();
        }
        runEndStep();
      }
    }
  }

  @Override
  public void run() {
    scenario.initializeScenarioThread(scenarioData, scenarioClassData, jobData,
        scenarioClassInitializer, jobInitializer, transactionRecorderImpl);

    runScenario();

    scenario.terminateScenarioThread();
  }
}