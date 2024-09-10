package com.airline.eureka_service_main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServiceMainApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServiceMainApplication.class, args);
	}

}
