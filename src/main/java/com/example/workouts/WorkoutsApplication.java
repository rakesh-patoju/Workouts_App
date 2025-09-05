package com.example.workouts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class WorkoutsApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext configurableApplicationcontext = SpringApplication.run(WorkoutsApplication.class, args);
		Arrays.stream(configurableApplicationcontext.getBeanDefinitionNames()).forEach(System.out::println);
	}

}
