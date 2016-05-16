package org.roy.test.integration;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.roy.loadx.ManualTest;
import org.roy.loadx.TestConfiguration;
import org.roy.loadx.invocation.LoadX;

@Category(ManualTest.class)
public class LoadXSampleJobIntTest {
	@Test
	public void runSampleJob() {
		new LoadX().run(new String[] { "sample/sample_job.js" }, new TestConfiguration());
	}
}