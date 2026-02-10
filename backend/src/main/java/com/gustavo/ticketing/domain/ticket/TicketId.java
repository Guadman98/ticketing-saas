package com.gustavo.ticketing.domain.ticket;

import java.util.UUID;

public record TicketId(UUID value) {
  public static TicketId newId() { return new TicketId(UUID.randomUUID()); }
}