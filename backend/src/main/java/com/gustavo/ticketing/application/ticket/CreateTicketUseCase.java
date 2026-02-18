package com.gustavo.ticketing.application.ticket;

import com.gustavo.ticketing.application.audit.RecordAuditEventUseCase;
import com.gustavo.ticketing.domain.ticket.Ticket;
import com.gustavo.ticketing.domain.ticket.TicketPriority;
import com.gustavo.ticketing.domain.ticket.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateTicketUseCase {
  private final TicketRepository ticketRepository;
  private final RecordAuditEventUseCase audit;

  public CreateTicketUseCase(TicketRepository ticketRepository, RecordAuditEventUseCase audit) {
    this.ticketRepository = ticketRepository;
    this.audit = audit;
  }

  public Ticket execute(UUID orgId, UUID createdBy, String title, String description, TicketPriority priority, String tags) {
    var ticket = Ticket.openNew(orgId, createdBy, title, description, priority, tags);
    var saved = ticketRepository.save(ticket);
    audit.execute(orgId, createdBy, "TICKET", saved.id().value(), "TICKET_CREATED", null);
    return saved;
  }
}