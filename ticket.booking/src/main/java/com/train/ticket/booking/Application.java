package com.train.ticket.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		System.out.println("Starting ticket booking application");
		SpringApplication.run(Application.class, args);
	}

}
