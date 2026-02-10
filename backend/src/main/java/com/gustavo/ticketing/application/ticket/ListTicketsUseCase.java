package com.gustavo.ticketing.application.ticket;

import com.gustavo.ticketing.domain.ticket.Ticket;
import com.gustavo.ticketing.domain.ticket.TicketRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ListTicketsUseCase {
  private final TicketRepository ticketRepository;

  public ListTicketsUseCase(TicketRepository ticketRepository) {
    this.ticketRepository = ticketRepository;
  }

  public Page<Ticket> execute(UUID orgId, TicketFilters filters, Pageable pageable) {
    return ticketRepository.findByOrg(orgId, filters, pageable);
  }
}