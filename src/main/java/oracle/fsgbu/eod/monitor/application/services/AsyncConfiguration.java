package oracle.fsgbu.eod.monitor.application.services;

import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(AsyncConfiguration.class);
	
	@Bean(name="taskexecutor")
	public Executor taskexecutor() {
		LOGGER.debug("Creating new Async task");
		final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(100);
		executor.setThreadNamePrefix("WLEureka Api-");
		executor.initialize();
		return executor;
	}
}
