package com.gustavo.ticketing.api.ticketcomment;

import com.gustavo.ticketing.api.ticketcomment.dto.CreateCommentRequest;
import com.gustavo.ticketing.api.ticketcomment.dto.TicketCommentResponse;
import com.gustavo.ticketing.application.ticketcomment.CreateCommentUseCase;
import com.gustavo.ticketing.application.ticketcomment.ListCommentsUseCase;
import com.gustavo.ticketing.infrastructure.config.BootstrapProperties;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tickets/{ticketId}/comments")
public class TicketCommentController {

  private final CreateCommentUseCase create;
  private final ListCommentsUseCase list;
  private final BootstrapProperties bootstrap;

  public TicketCommentController(CreateCommentUseCase create, ListCommentsUseCase list, BootstrapProperties bootstrap) {
    this.create = create;
    this.list = list;
    this.bootstrap = bootstrap;
  }

  @PostMapping
  public TicketCommentResponse create(@PathVariable UUID ticketId, @Valid @RequestBody CreateCommentRequest req) {
    var orgId = bootstrap.orgId();
    var authorId = bootstrap.adminUserId(); // por ahora (hasta JWT)
    var comment = create.execute(orgId, ticketId, authorId, req.visibility(), req.body());
    return TicketCommentResponse.from(comment);
  }

  @GetMapping
  public Page<TicketCommentResponse> list(@PathVariable UUID ticketId, Pageable pageable) {
    return list.execute(bootstrap.orgId(), ticketId, pageable)
        .map(TicketCommentResponse::from);
  }
}
