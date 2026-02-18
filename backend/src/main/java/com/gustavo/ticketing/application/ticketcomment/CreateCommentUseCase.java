package com.gustavo.ticketing.application.ticketcomment;

import com.gustavo.ticketing.application.audit.RecordAuditEventUseCase;
import com.gustavo.ticketing.domain.ticketcomment.CommentVisibility;
import com.gustavo.ticketing.domain.ticketcomment.TicketComment;
import com.gustavo.ticketing.domain.ticketcomment.TicketCommentRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateCommentUseCase {
  private final TicketCommentRepository repo;
  private final RecordAuditEventUseCase audit;

  public CreateCommentUseCase(TicketCommentRepository repo, RecordAuditEventUseCase audit) {
    this.repo = repo;
    this.audit = audit;
  }

  public TicketComment execute(UUID orgId, UUID ticketId, UUID authorId, CommentVisibility visibility, String body) {
    var comment = TicketComment.create(ticketId, orgId, authorId, visibility, body);
    var saved = repo.save(comment);
    audit.execute(orgId, authorId, "TICKET_COMMENT", saved.id().value(), "COMMENT_CREATED", null);
    return saved;
  }
}
