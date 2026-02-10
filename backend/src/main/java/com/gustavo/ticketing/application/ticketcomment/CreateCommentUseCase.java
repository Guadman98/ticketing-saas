package com.gustavo.ticketing.application.ticketcomment;

import com.gustavo.ticketing.domain.ticketcomment.CommentVisibility;
import com.gustavo.ticketing.domain.ticketcomment.TicketComment;
import com.gustavo.ticketing.domain.ticketcomment.TicketCommentRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateCommentUseCase {
  private final TicketCommentRepository repo;

  public CreateCommentUseCase(TicketCommentRepository repo) {
    this.repo = repo;
  }

  public TicketComment execute(UUID orgId, UUID ticketId, UUID authorId, CommentVisibility visibility, String body) {
    var comment = TicketComment.create(ticketId, orgId, authorId, visibility, body);
    return repo.save(comment);
  }
}
