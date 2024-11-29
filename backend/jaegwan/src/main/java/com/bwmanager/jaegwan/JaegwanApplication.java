package com.bwmanager.jaegwan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableFeignClients
public class JaegwanApplication {

	public static void main(String[] args) {
		SpringApplication.run(JaegwanApplication.class, args);
	}

}
