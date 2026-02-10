package com.gustavo.ticketing.api.ticketcomment.dto;

import com.gustavo.ticketing.domain.ticketcomment.TicketComment;

import java.time.Instant;
import java.util.UUID;

public record TicketCommentResponse(
    UUID id,
    UUID ticketId,
    String visibility,
    String body,
    Instant createdAt
) {
  public static TicketCommentResponse from(TicketComment c) {
    return new TicketCommentResponse(
        c.id().value(),
        c.ticketId(),
        c.visibility().name(),
        c.body(),
        c.createdAt()
    );
  }
}
