package com.gustavo.ticketing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TicketFlowIT {

  @Container
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
      .withDatabaseName("ticketing")
      .withUsername("ticketing")
      .withPassword("ticketing");

  @DynamicPropertySource
  static void props(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);

    // Asegura que Flyway corre en el contenedor
    registry.add("spring.flyway.enabled", () -> "true");
  }

  @Autowired
  TestRestTemplate http;

  @Test
  void createTicket_thenCreateComment_thenListBoth() {
    // 1) Create ticket
    var createTicketBody = Map.of(
        "title", "No puedo iniciar sesión",
        "description", "Me da error 500",
        "priority", "HIGH",
        "tags", "login"
    );

    var ticketResp = http.postForEntity("/api/v1/tickets", createTicketBody, Map.class);
    assertThat(ticketResp.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(ticketResp.getBody()).isNotNull();
    assertThat(ticketResp.getBody()).containsKey("id");

    var ticketId = String.valueOf(ticketResp.getBody().get("id"));
    assertThat(ticketId).isNotBlank();

    // 2) Create comment
    var createCommentBody = Map.of(
        "visibility", "PUBLIC",
        "body", "Estoy revisando el incidente."
    );

    var commentResp = http.postForEntity("/api/v1/tickets/" + ticketId + "/comments", createCommentBody, Map.class);
    assertThat(commentResp.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(commentResp.getBody()).isNotNull();
    assertThat(String.valueOf(commentResp.getBody().get("ticketId"))).isEqualTo(ticketId);

    // 3) List tickets
    var listTickets = http.getForEntity("/api/v1/tickets?page=0&size=10", Map.class);
    assertThat(listTickets.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(listTickets.getBody()).isNotNull();
    assertThat(listTickets.getBody()).containsKey("content");

    // 4) List comments
    var listComments = http.getForEntity("/api/v1/tickets/" + ticketId + "/comments?page=0&size=10", Map.class);
    assertThat(listComments.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(listComments.getBody()).isNotNull();
    assertThat(listComments.getBody()).containsKey("content");
  }
}
