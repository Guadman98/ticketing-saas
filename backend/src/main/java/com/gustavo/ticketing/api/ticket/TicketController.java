package com.gustavo.ticketing.api.ticket;

import com.gustavo.ticketing.api.ticket.dto.CreateTicketRequest;
import com.gustavo.ticketing.api.ticket.dto.TicketResponse;
import com.gustavo.ticketing.application.ticket.CreateTicketUseCase;
import com.gustavo.ticketing.application.ticket.ListTicketsUseCase;
import com.gustavo.ticketing.application.ticket.TicketFilters;
import com.gustavo.ticketing.domain.ticket.TicketPriority;
import com.gustavo.ticketing.domain.ticket.TicketStatus;
import com.gustavo.ticketing.infrastructure.config.BootstrapProperties;
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
  private final BootstrapProperties bootstrap;

  public TicketController(CreateTicketUseCase create, ListTicketsUseCase list, BootstrapProperties bootstrap) {
    this.create = create;
    this.list = list;
    this.bootstrap = bootstrap;
  }

  @PostMapping
  public TicketResponse create(@Valid @RequestBody CreateTicketRequest req) {
    UUID orgId = bootstrap.orgId();
    UUID createdBy = bootstrap.adminUserId();

    var ticket = create.execute(orgId, createdBy, req.title(), req.description(), req.priority(), req.tags());
    return TicketResponse.from(ticket);
  }

  @GetMapping
  public Page<TicketResponse> list(
      @RequestParam(required = false) TicketStatus status,
      @RequestParam(required = false) TicketPriority priority,
      @RequestParam(required = false) UUID assignedTo,
      @RequestParam(required = false) String q,
      Pageable pageable
  ) {
    var filters = new TicketFilters(status, priority, assignedTo, q);
    return list.execute(bootstrap.orgId(), filters, pageable).map(TicketResponse::from);
  }
}