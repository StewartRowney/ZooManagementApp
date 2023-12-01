package com.example.zoo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Zoo Management API", version = "1.0.0", description = "Managing Zoos in the Solar System") )
public class ZooManagementAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZooManagementAppApplication.class, args);
	}

}
