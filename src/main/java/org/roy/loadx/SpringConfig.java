package org.roy.loadx;

import org.roy.loadx.transaction.TimeProvider;
import org.roy.loadx.transaction.TimeProviderImpl;
import org.roy.loadx.transaction.TransactionPrintRunner;
import org.roy.loadx.transaction.TransactionPrintRunnerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class SpringConfig {
	@Bean
	public TimeProvider timeProvider() {
		return new TimeProviderImpl();
	}

	@Bean
	public TransactionPrintRunner transactionPrintRunner() {
		return new TransactionPrintRunnerImpl();
	}
}
