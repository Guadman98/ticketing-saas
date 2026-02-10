package com.gustavo.ticketing.application.ticket;

import com.gustavo.ticketing.domain.ticket.Ticket;
import com.gustavo.ticketing.domain.ticket.TicketPriority;
import com.gustavo.ticketing.domain.ticket.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateTicketUseCase {
  private final TicketRepository ticketRepository;

  public CreateTicketUseCase(TicketRepository ticketRepository) {
    this.ticketRepository = ticketRepository;
  }

  public Ticket execute(UUID orgId, UUID createdBy, String title, String description, TicketPriority priority, String tags) {
    var ticket = Ticket.openNew(orgId, createdBy, title, description, priority, tags);
    return ticketRepository.save(ticket);
  }
}