package com.BILTMS.CS319_TA_Management_System;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ComponentScan(basePackages = {"com.services", "com.repositories", "com.controllers", "com.security" })  // Adjust as needed
@EnableJpaRepositories(basePackages = "com.repositories")  // Ensure this matches the package where CourseRepository is located
//@ComponentScan(basePackages = {"com.example.app", "com.example.app.controllers"})
@EntityScan("com.entities")  // Ensure this matches the package where Course is located

@RestController
public class Cs319TaManagementSystemApplication {

	private static final Logger logger = LoggerFactory.getLogger(Cs319TaManagementSystemApplication.class);
	public static void main(String[] args) {
		//System.out.println("CS319 TA Management System is starting...");
		// Password encoding example (for demonstration purposes only, not for production use)
		//BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		//System.out.println( passwordEncoder.encode("1234")); // Example password encoding
		SpringApplication.run(Cs319TaManagementSystemApplication.class, args);

	}
}