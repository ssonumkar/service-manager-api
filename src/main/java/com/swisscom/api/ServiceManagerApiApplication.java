package com.swisscom.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ServiceManagerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceManagerApiApplication.class, args);
	}

}
