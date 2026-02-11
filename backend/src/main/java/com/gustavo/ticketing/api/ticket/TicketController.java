package com.gustavo.ticketing.api.ticket;

import com.gustavo.ticketing.api.ticket.dto.CreateTicketRequest;
import com.gustavo.ticketing.api.ticket.dto.TicketResponse;
import com.gustavo.ticketing.application.ticket.CreateTicketUseCase;
import com.gustavo.ticketing.application.ticket.ListTicketsUseCase;
import com.gustavo.ticketing.application.ticket.TicketFilters;
import com.gustavo.ticketing.domain.ticket.TicketPriority;
import com.gustavo.ticketing.domain.ticket.TicketStatus;
import com.gustavo.ticketing.infrastructure.config.BootstrapProperties;
import com.gustavo.ticketing.domain.auth.AuthPrincipal;
import org.springframework.security.core.Authentication;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

  private final CreateTicketUseCase create;
  private final ListTicketsUseCase list;

  public TicketController(CreateTicketUseCase create, ListTicketsUseCase list) {
    this.create = create;
    this.list = list;
  }

  @PostMapping
  public TicketResponse create(@Valid @RequestBody CreateTicketRequest req, Authentication auth) {
    var p = (AuthPrincipal) auth.getPrincipal();
    var ticket = create.execute(p.orgId(), p.userId(), req.title(), req.description(), req.priority(), req.tags());
    return TicketResponse.from(ticket);
  }

  @GetMapping
  public Page<TicketResponse> list(
      @RequestParam(required = false) TicketStatus status,
      @RequestParam(required = false) TicketPriority priority,
      @RequestParam(required = false) UUID assignedTo,
      @RequestParam(required = false) String q,
      Pageable pageable,
      Authentication auth) {
    var p = (AuthPrincipal) auth.getPrincipal();

    UUID createdBy = "CUSTOMER".equalsIgnoreCase(p.role()) ? p.userId() : null;

    var filters = new TicketFilters(status, priority, assignedTo, q, createdBy);
    return list.execute(p.orgId(), filters, pageable).map(TicketResponse::from);
  }
}