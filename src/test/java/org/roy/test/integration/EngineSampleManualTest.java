package org.roy.test.integration;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.roy.loadx.priv.engine.Engine;
import org.roy.loadx.priv.engine.TestConfiguration;
import org.roy.loadx.test.ManualTest;

@Category(ManualTest.class)
public class EngineSampleManualTest {
	@Test
	public void sampleJob() {
		new Engine().run(new String[] { "sample/sample_job.js" }, new TestConfiguration());
	}
}