package org.roy.loadx.sample;

import org.roy.loadx.pub.api.ExecutionData;
import org.roy.loadx.pub.api.JobInitializer;
import org.roy.loadx.pub.api.Scenario;
import org.roy.loadx.pub.api.ScenarioClassInitializer;
import org.roy.loadx.pub.api.TransactionRecorder;

public class LongRunningScenario implements Scenario {
  private TransactionRecorder transactionRecorder;

  @Override
  public void initializeScenarioThread(ExecutionData scenarioData, ExecutionData scenarioClassData,
      ExecutionData jobData, ScenarioClassInitializer scenarioClassInitializer,
      JobInitializer jobInitializer, TransactionRecorder transactionRecorder) {
    this.transactionRecorder = transactionRecorder;
  }

  @Override
  public void start() {
    transactionRecorder.start("start");
    sleep(3);
    transactionRecorder.end();
  }

  @Override
  public void run() {
    transactionRecorder.start("run");
    sleep(5);
    transactionRecorder.end();
  }

  @Override
  public void end() {
    transactionRecorder.start("end");
    sleep(3);
    transactionRecorder.end();
  }

  private void sleep(int delayRangeSeconds) {
    int delaySeconds = (int) (Math.random() * (delayRangeSeconds + 1));
    try {
      Thread.sleep(delaySeconds * 1000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void terminateScenarioThread() {
  }
}