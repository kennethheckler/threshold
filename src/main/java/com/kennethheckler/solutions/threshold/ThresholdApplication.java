package com.kennethheckler.solutions.threshold;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * The main entry point and root level package Spring Boot uses when bootstrapping this application. The application is
 * a cloud-native, microservice, providing a single rest endpoint to allow the user to retrieve the most active authors
 * from an external HackerRank Rest API data source. The exposed endpoint gives the user the ability to tweak the number
 * of users returned by passing an approval percentage threshold value.
 *
 * @author Kenneth Heckler
 */
@SpringBootApplication
@ConfigurationPropertiesScan("com.kennethheckler.solutions.threshold")
public class ThresholdApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThresholdApplication.class, args);
	}
}
