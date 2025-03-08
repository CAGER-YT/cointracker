package com.rooter.cointracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CointrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CointrackerApplication.class, args);
	}

}
