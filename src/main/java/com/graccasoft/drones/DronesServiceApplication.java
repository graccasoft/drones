package com.graccasoft.drones;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DronesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DronesServiceApplication.class, args);
	}


	@Bean
	public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
		return new OpenAPI()
				.info(new Info()
						.title("Drones Service API")
						.version(appVersion)
						.description("This is a simple REST API for dispatching drones"));
	}
}

