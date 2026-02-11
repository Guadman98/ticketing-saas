package com.gustavo.ticketing.api.ticketcomment;

import com.gustavo.ticketing.api.ticketcomment.dto.CreateCommentRequest;
import com.gustavo.ticketing.api.ticketcomment.dto.TicketCommentResponse;
import com.gustavo.ticketing.application.ticketcomment.CreateCommentUseCase;
import com.gustavo.ticketing.application.ticketcomment.ListCommentsUseCase;
import com.gustavo.ticketing.domain.auth.AuthPrincipal;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tickets/{ticketId}/comments")
public class TicketCommentController {

  private final CreateCommentUseCase create;
  private final ListCommentsUseCase list;

  public TicketCommentController(CreateCommentUseCase create, ListCommentsUseCase list) {
    this.create = create;
    this.list = list;
  }

  @PostMapping
  public TicketCommentResponse create(@PathVariable UUID ticketId,
      @Valid @RequestBody CreateCommentRequest req,
      Authentication auth) {
    var p = (AuthPrincipal) auth.getPrincipal();

    if (req.visibility() != null && "INTERNAL".equalsIgnoreCase(req.visibility().name())
        && "CUSTOMER".equalsIgnoreCase(p.role())) {
      throw new org.springframework.web.server.ResponseStatusException(
          org.springframework.http.HttpStatus.FORBIDDEN, "CUSTOMER cannot create INTERNAL comments");
    }

    var comment = create.execute(p.orgId(), ticketId, p.userId(), req.visibility(), req.body());
    return TicketCommentResponse.from(comment);
  }

  @GetMapping
  public Page<TicketCommentResponse> list(@PathVariable UUID ticketId, Pageable pageable, Authentication auth) {
    var p = (AuthPrincipal) auth.getPrincipal();
    return list.execute(p.orgId(), ticketId, pageable).map(TicketCommentResponse::from);
  }
}
