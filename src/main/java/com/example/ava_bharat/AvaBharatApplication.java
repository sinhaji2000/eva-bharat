package com.example.ava_bharat;

import java.net.URI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AvaBharatApplication {

	public static void main(String[] args) {
		useJawsDbUrlIfPresent();
		SpringApplication.run(AvaBharatApplication.class, args);
	}

	// Heroku's JawsDB add-on provides JAWSDB_URL as mysql://user:pass@host:port/db.
	// Convert it to Spring datasource settings so no manual config vars are needed.
	private static void useJawsDbUrlIfPresent() {
		String jawsDbUrl = System.getenv("JAWSDB_URL");
		if (jawsDbUrl == null || jawsDbUrl.isBlank()) {
			return;
		}
		URI uri = URI.create(jawsDbUrl);
		String[] credentials = uri.getUserInfo().split(":", 2);
		System.setProperty("spring.datasource.url",
				"jdbc:mysql://" + uri.getHost() + ":" + uri.getPort() + uri.getPath() + "?useSSL=true");
		System.setProperty("spring.datasource.username", credentials[0]);
		System.setProperty("spring.datasource.password", credentials[1]);
	}
}
