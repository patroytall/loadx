package org.roy.test.integration;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.roy.loadx.pub.invocation.LoadX;
import org.roy.loadx.test.ManualTest;

@Category(ManualTest.class)
public class LoadXInvoker {
  @Test
  public void invokeSampleJob() {
    LoadX.main(new String[] { "sample/sample_job.js" });
  }

  @Test
  public void invokeLongRunning() {
    LoadX.main(new String[] { "long-running_job.js" });
  }
}
