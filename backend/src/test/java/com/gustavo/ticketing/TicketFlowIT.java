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

        registry.add("spring.flyway.enabled", () -> "true");

        // JWT config para tests (no dependas del application.yml del dev)
        registry.add("app.security.jwt.secret", () -> "TEST_SECRET_TEST_SECRET_TEST_SECRET_1234567890");
        registry.add("app.security.jwt.issuer", () -> "ticketing-saas");
        registry.add("app.security.jwt.access-token-minutes", () -> "60");
    }

    @Autowired
    TestRestTemplate http;

    @Test
    void createTicket_thenCreateComment_thenListBoth() {
        // 0) Login (usuario seed: admin@dev.local / dev)
        var loginResp = http.postForEntity(
                "/api/v1/auth/login",
                Map.of("email", "admin@dev.local", "password", "dev"),
                Map.class);

        assertThat(loginResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(loginResp.getBody()).isNotNull();
        assertThat(loginResp.getBody()).containsKey("accessToken");

        String token = String.valueOf(loginResp.getBody().get("accessToken"));
        assertThat(token).isNotBlank();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 1) Create ticket
        var ticketReq = new HttpEntity<>(Map.of(
                "title", "No puedo iniciar sesión",
                "description", "Me da error 500",
                "priority", "HIGH",
                "tags", "login"), headers);

        var ticketResp = http.exchange("/api/v1/tickets", HttpMethod.POST, ticketReq, Map.class);
        assertThat(ticketResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(ticketResp.getBody()).isNotNull();
        assertThat(ticketResp.getBody()).containsKey("id");

        String ticketId = String.valueOf(ticketResp.getBody().get("id"));
        assertThat(ticketId).isNotBlank();

        // 2) Create comment
        var commentReq = new HttpEntity<>(Map.of(
                "visibility", "PUBLIC",
                "body", "Estoy revisando el incidente."), headers);

        var commentResp = http.exchange(
                "/api/v1/tickets/" + ticketId + "/comments",
                HttpMethod.POST,
                commentReq,
                Map.class);

        assertThat(commentResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(commentResp.getBody()).isNotNull();
        assertThat(String.valueOf(commentResp.getBody().get("ticketId"))).isEqualTo(ticketId);

        // 3) List tickets
        var listTicketsReq = new HttpEntity<>(headers);
        var listTicketsResp = http.exchange(
                "/api/v1/tickets?page=0&size=10",
                HttpMethod.GET,
                listTicketsReq,
                Map.class);

        assertThat(listTicketsResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(listTicketsResp.getBody()).isNotNull();
        assertThat(listTicketsResp.getBody()).containsKey("content");

        // 4) List comments
        var listCommentsResp = http.exchange(
                "/api/v1/tickets/" + ticketId + "/comments?page=0&size=10",
                HttpMethod.GET,
                listTicketsReq,
                Map.class);

        assertThat(listCommentsResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(listCommentsResp.getBody()).isNotNull();
        assertThat(listCommentsResp.getBody()).containsKey("content");
    }
}
