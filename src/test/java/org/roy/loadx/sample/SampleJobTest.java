package org.roy.loadx.sample;

import org.junit.Test;
import org.roy.loadx.invocation.LoadX;

public class SampleJobTest {

	@Test
	public void runSampleJob() {
		LoadX.main(new String[] { "sample/sample_job.js" });
	}
}
