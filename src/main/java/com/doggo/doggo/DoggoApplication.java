package com.doggo.doggo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DoggoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoggoApplication.class, args);

	}

}
