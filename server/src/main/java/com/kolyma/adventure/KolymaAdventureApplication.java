package com.kolyma.adventure;

import org.flywaydb.core.Flyway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KolymaAdventureApplication {

	public static void main(String[] args) {
		SpringApplication.run(KolymaAdventureApplication.class, args);
	}

	@Bean
	public CommandLineRunner repairDatabase(Flyway flyway) {
		return args -> {
			flyway.repair(); // обновит checksum в flyway_schema_history
		};
	}

}