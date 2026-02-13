package com.gustavo.ticketing.application.ticketcomment;

import com.gustavo.ticketing.domain.ticketcomment.TicketComment;
import com.gustavo.ticketing.domain.ticketcomment.TicketCommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ListCommentsForUserUseCase {

  private final TicketCommentRepository repo;

  public ListCommentsForUserUseCase(TicketCommentRepository repo) {
    this.repo = repo;
  }

  public Page<TicketComment> execute(UUID orgId, UUID ticketId, String role, Pageable pageable) {
    if ("CUSTOMER".equalsIgnoreCase(role)) {
      return repo.findPublicByTicket(orgId, ticketId, pageable);
    }
    return repo.findByTicket(orgId, ticketId, pageable);
  }
}
