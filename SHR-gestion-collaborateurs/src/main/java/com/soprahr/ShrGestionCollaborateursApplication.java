package com.soprahr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ShrGestionCollaborateursApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShrGestionCollaborateursApplication.class, args);
	}

}
