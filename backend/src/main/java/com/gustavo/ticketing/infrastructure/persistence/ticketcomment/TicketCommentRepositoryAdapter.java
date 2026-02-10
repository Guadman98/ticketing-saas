package com.gustavo.ticketing.infrastructure.persistence.ticketcomment;

import com.gustavo.ticketing.domain.ticketcomment.TicketComment;
import com.gustavo.ticketing.domain.ticketcomment.TicketCommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class TicketCommentRepositoryAdapter implements TicketCommentRepository {

  private final TicketCommentJpaRepository jpa;

  public TicketCommentRepositoryAdapter(TicketCommentJpaRepository jpa) {
    this.jpa = jpa;
  }

  @Override
  public TicketComment save(TicketComment comment) {
    var saved = jpa.save(TicketCommentMapper.toEntity(comment));
    return TicketCommentMapper.toDomain(saved);
  }

  @Override
  public Page<TicketComment> findByTicket(UUID orgId, UUID ticketId, Pageable pageable) {
    return jpa.findByOrgIdAndTicketIdOrderByCreatedAtAsc(orgId, ticketId, pageable)
        .map(TicketCommentMapper::toDomain);
  }
}
