package com.gustavo.ticketing.api.ticket.dto;

import com.gustavo.ticketing.domain.ticket.Ticket;

import java.time.Instant;
import java.util.UUID;

public record TicketResponse(
    UUID id,
    String title,
    String status,
    String priority,
    Instant createdAt,
    Instant updatedAt
) {
  public static TicketResponse from(Ticket t) {
    return new TicketResponse(
        t.id().value(),
        t.title(),
        t.status().name(),
        t.priority().name(),
        t.createdAt(),
        t.updatedAt()
    );
  }
}