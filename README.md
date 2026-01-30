# Ticketing SaaS (Java 21 + Spring Boot + Angular)

SaaS-style ticketing system for B2B support teams. Includes authentication, roles, multi-tenant organizations, ticket lifecycle, comments (public/internal), audit events, observability, and integration tests.

## Tech Stack
- Backend: Java 21, Spring Boot 3, Spring Security, JPA, Flyway
- DB: PostgreSQL
- Infra: Docker Compose
- Testing: JUnit 5, Testcontainers
- Frontend: Angular (planned)

## Features (MVP)
- JWT authentication + roles (ADMIN, AGENT, CUSTOMER)
- Organizations (multi-tenant via org_id)
- Tickets: create, assign, status workflow, filters + pagination
- Comments: PUBLIC / INTERNAL
- Audit events for key actions
- Actuator health + metrics
- Integration tests with Testcontainers

## Project Structure
- backend/   Spring Boot API
- frontend/  Angular web app
- infra/     Docker compose and scripts
- docs/      Architecture notes and diagrams

## Run locally

### Start database
docker compose -f infra/docker-compose.yml up -d

### Run backend
DB_URL=jdbc:postgresql://<YOUR_HOST_IP>:5432/ticketing DB_USER=ticketing DB_PASS=ticketing mvn -f backend/pom.xml spring-boot:run
