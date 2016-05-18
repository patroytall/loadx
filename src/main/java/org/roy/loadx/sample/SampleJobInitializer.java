package org.roy.loadx.sample;

import org.roy.loadx.api.ExecutionData;
import org.roy.loadx.api.JobInitializer;

public class SampleJobInitializer implements JobInitializer {
  public enum Data {
    JOB
  }

  @Override
	public void initializeJob(ExecutionData jobData) {
		println("initialize job - job data - job - " + jobData.getString(Data.JOB));
	}

	@Override
	public void terminateJob() {
    println("terminate job");
	}

  public String getSimpleName() {
    return getClass().getSimpleName();
  }
  
  private static void println(String str) {
    System.out.println(str);
    System.out.flush();
  }
}