package com.gustavo.ticketing.infrastructure.persistence.ticketcomment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketCommentJpaRepository extends JpaRepository<TicketCommentJpaEntity, UUID> {
  Page<TicketCommentJpaEntity> findByOrgIdAndTicketIdOrderByCreatedAtAsc(UUID orgId, UUID ticketId, Pageable pageable);

  Page<TicketCommentJpaEntity> findByOrgIdAndTicketIdAndVisibilityOrderByCreatedAtAsc(
      UUID orgId, UUID ticketId, String visibility, Pageable pageable);
}
