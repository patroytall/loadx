package org.roy.loadx.pub.sample;

import org.roy.loadx.pub.api.ExecutionData;
import org.roy.loadx.pub.api.JobInitializer;
import org.roy.loadx.pub.api.Scenario;
import org.roy.loadx.pub.api.ScenarioClassInitializer;
import org.roy.loadx.pub.api.TransactionRecorder;

public class LongRunningScenario implements Scenario {
  private TransactionRecorder transactionRecorder;

  private int runCount = 0;
  
  @Override
  public void initializeScenarioThread(ExecutionData scenarioData, ExecutionData scenarioClassData,
      ExecutionData jobData, ScenarioClassInitializer scenarioClassInitializer,
      JobInitializer jobInitializer, TransactionRecorder transactionRecorder) {
    this.transactionRecorder = transactionRecorder;
  }

  @Override
  public void start() {
    transactionRecorder.start("start");
    sleep(300);
    transactionRecorder.end();
  }

  @Override
  public void run() {
    transactionRecorder.start("run");
    sleep(500);
    sleep(runCount++ % 500);
    transactionRecorder.end();
  }

  @Override
  public void end() {
    transactionRecorder.start("end");
    sleep(200);
    transactionRecorder.end();
  }

  private void sleep(int delayRangeMillis) {
    int delayMillis = (int) (Math.random() * delayRangeMillis) + 1;
    try {
      Thread.sleep(delayMillis);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void terminateScenarioThread() {
  }
}