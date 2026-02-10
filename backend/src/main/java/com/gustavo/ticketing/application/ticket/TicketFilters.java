package com.gustavo.ticketing.application.ticket;

import com.gustavo.ticketing.domain.ticket.TicketPriority;
import com.gustavo.ticketing.domain.ticket.TicketStatus;

import java.util.UUID;

public record TicketFilters(
    TicketStatus status,
    TicketPriority priority,
    UUID assignedTo,
    String q
) {}