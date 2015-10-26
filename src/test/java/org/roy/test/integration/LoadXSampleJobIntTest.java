package org.roy.test.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.roy.loadx.SpringTestConfig;
import org.roy.loadx.TestTimeProvider;
import org.roy.loadx.invocation.LoadX;
import org.roy.loadx.transaction.TimeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@ContextConfiguration(classes = SpringTestConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class })
public class LoadXSampleJobIntTest {
	@Bean
	TimeProvider timeProvider() {
		return new TestTimeProvider();
	}

	@Autowired
	LoadX loadX;

	@Test
	public void runSampleJob() {
		loadX.run(new String[] { "sample/sample_job.js" });
	}
}
