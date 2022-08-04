package com.nttdata.bootcamp.passive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Passive Application.
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class PassiveApplication {

  public static void main(String[] args) {
    SpringApplication.run(PassiveApplication.class, args);
  }

}
