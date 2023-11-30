package com.example.zoo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.info.*;

import static java.awt.SystemColor.info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Zoo API", version = "1.0.0", description = "Managing Zoos in the UK") )
public class ZooManagementAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZooManagementAppApplication.class, args);
	}

}
