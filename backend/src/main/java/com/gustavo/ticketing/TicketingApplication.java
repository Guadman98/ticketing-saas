package com.gustavo.ticketing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.gustavo.ticketing.infrastructure.config.BootstrapProperties;
import com.gustavo.ticketing.infrastructure.security.JwtProperties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({ BootstrapProperties.class, JwtProperties.class })
@SpringBootApplication
public class TicketingApplication {
  public static void main(String[] args) {
    SpringApplication.run(TicketingApplication.class, args);
  }
}
