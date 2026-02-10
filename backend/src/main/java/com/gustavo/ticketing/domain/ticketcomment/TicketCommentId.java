package com.gustavo.ticketing.domain.ticketcomment;

import java.util.UUID;

public record TicketCommentId(UUID value) {
  public static TicketCommentId newId() { return new TicketCommentId(UUID.randomUUID()); }
}
