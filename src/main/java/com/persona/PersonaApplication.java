package com.persona;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Eureka Client.
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class PersonaApplication {

  public static void main(String[] args) {
    SpringApplication.run(PersonaApplication.class, args);
  }

}
