package com.gustavo.ticketing.infrastructure.persistence.ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface TicketJpaRepository extends JpaRepository<TicketJpaEntity, UUID>, JpaSpecificationExecutor<TicketJpaEntity> {}