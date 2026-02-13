package com.gustavo.ticketing.domain.ticketcomment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TicketCommentRepository {
  TicketComment save(TicketComment comment);

  Page<TicketComment> findByTicket(UUID orgId, UUID ticketId, Pageable pageable);

  Page<TicketComment> findPublicByTicket(UUID orgId, UUID ticketId, Pageable pageable);

}
