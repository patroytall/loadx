package org.roy.test.integration;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.roy.loadx.EngineConfiguration;
import org.roy.loadx.ManualTest;
import org.roy.loadx.invocation.LoadX;

@Category(ManualTest.class)
public class LoadXInvoker {
  @Test
  public void invokeSampleJob() {
    new LoadX().run(new String[] { "sample/sample_job.js" }, new EngineConfiguration());
  }
}
