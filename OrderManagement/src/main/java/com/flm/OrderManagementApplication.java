package com.flm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;

@SpringBootApplication
public class OrderManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderManagementApplication.class, args);
	}

	@Bean
	public TimedAspect timedAspect(MeterRegistry meterRegistry) {
		return new TimedAspect(meterRegistry);
		
	}
	@Bean
	public CountedAspect countedAspect(MeterRegistry meterRegistry) {
		return new CountedAspect(meterRegistry);
		
	}
}
