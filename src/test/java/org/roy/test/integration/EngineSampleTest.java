package org.roy.test.integration;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.roy.loadx.ManualTest;
import org.roy.loadx.TestConfiguration;
import org.roy.loadx.priv.engine.Engine;

@Category(ManualTest.class)
public class EngineSampleTest {
	@Test
	public void sampleJob() {
		new Engine().run(new String[] { "sample/sample_job.js" }, new TestConfiguration());
	}
}