package org.roy.loadx;

import org.roy.loadx.transaction.TimeProvider;
import org.roy.loadx.transaction.TimeProviderImpl;
import org.roy.loadx.transaction.TransactionPrintRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class SpringTestConfig {
	@Bean
	public TimeProvider timeProvider() {
		return new TimeProviderImpl();
	}

	@Bean
	public TransactionPrintRunner transactionPrintRunner() {
		return new TestTransactionPrintRunner();
	}
}