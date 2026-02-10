package com.gustavo.ticketing.domain.ticket;

import com.gustavo.ticketing.application.ticket.TicketFilters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TicketRepository {
  Ticket save(Ticket ticket);
  Page<Ticket> findByOrg(UUID orgId, TicketFilters filters, Pageable pageable);
}