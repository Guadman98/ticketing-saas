package com.gustavo.ticketing.infrastructure.persistence.ticketcomment;

import com.gustavo.ticketing.domain.ticketcomment.*;

public final class TicketCommentMapper {
  private TicketCommentMapper() {}

  public static TicketCommentJpaEntity toEntity(TicketComment c) {
    var e = new TicketCommentJpaEntity();
    e.setId(c.id().value());
    e.setTicketId(c.ticketId());
    e.setOrgId(c.orgId());
    e.setAuthorId(c.authorId());
    e.setVisibility(c.visibility().name());
    e.setBody(c.body());
    e.setCreatedAt(c.createdAt());
    return e;
  }

  public static TicketComment toDomain(TicketCommentJpaEntity e) {
    return TicketComment.rehydrate(
        new TicketCommentId(e.getId()),
        e.getTicketId(),
        e.getOrgId(),
        e.getAuthorId(),
        CommentVisibility.valueOf(e.getVisibility()),
        e.getBody(),
        e.getCreatedAt()
    );
  }
}
