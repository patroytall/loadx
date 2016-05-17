package org.roy.loadx.sample;

import org.roy.loadx.api.ExecutionData;
import org.roy.loadx.api.JobInitializer;

public class SampleJobInitializer implements JobInitializer {
  public enum Data {
    PROJECT
  }
	@Override
	public void initializeJob(ExecutionData jobData) {
		println("initialize job - job data - project - " + jobData.getString(Data.PROJECT));
	}

	@Override
	public void terminateJob(ExecutionData jobData) {
		println("terminate job - job data - project - " + jobData.getString(Data.PROJECT));
	}

  private static void println(String str) {
    System.out.println(str);
    System.out.flush();
  }
}