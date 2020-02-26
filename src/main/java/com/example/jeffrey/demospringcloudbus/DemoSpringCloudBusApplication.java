package com.example.jeffrey.demospringcloudbus;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoSpringCloudBusApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoSpringCloudBusApplication.class, args);
	}

	@Autowired
	CachingConnectionFactory connectionFactory;

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			/**
			 * Causes a re-connection issue with auto-delete queues (used by Spring Bus)
			 * when the underlying RabbitMQ connection factory has autoRecoveryEnabled=true
			 *
			 * Normally spring amqp disables it, but if you supply a rabbitmq connection
			 * factory with it set, it will cause this problem
			 *
			 * Running on PCF, spring-cloud-connectors prior to 2.0.3 leaves this true.
			 *
			 * See also:
			 * - https://jira.spring.io/browse/AMQP-834
			 * - https://github.com/spring-projects/spring-amqp/issues/818
			 * - https://stackoverflow.com/questions/57400451/spring-cloud-busamqp-not-recreating-queues-after-rabbitmq-restarted
			 */
			connectionFactory.getRabbitConnectionFactory().setAutomaticRecoveryEnabled(true);

			boolean autoRecovery = connectionFactory.getRabbitConnectionFactory().isAutomaticRecoveryEnabled();
			System.out.println(autoRecovery);
		};
	}

}
