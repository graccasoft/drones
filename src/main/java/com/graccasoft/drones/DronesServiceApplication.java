package com.graccasoft.drones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DronesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DronesServiceApplication.class, args);
	}

}
