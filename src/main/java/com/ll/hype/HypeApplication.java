package com.ll.hype;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HypeApplication {

	public static void main(String[] args) {
		SpringApplication.run(HypeApplication.class, args);
	}

}
