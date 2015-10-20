package org.roy.loadx.sample;

import org.roy.loadx.api.JobInitializer;

public class SampleJobInitializer implements JobInitializer {

	@Override
	public void initialize() {
		System.out.println("job initialize");
	}

	@Override
	public void terminate() {
		System.out.println("job terminate");
	}
}
