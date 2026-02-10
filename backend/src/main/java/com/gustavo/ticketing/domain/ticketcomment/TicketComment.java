package com.gustavo.ticketing.domain.ticketcomment;

import java.time.Instant;
import java.util.UUID;

public class TicketComment {
  private final TicketCommentId id;
  private final UUID ticketId;
  private final UUID orgId;
  private final UUID authorId;

  private final CommentVisibility visibility;
  private final String body;

  private final Instant createdAt;

  private TicketComment(TicketCommentId id, UUID ticketId, UUID orgId, UUID authorId,
                        CommentVisibility visibility, String body, Instant createdAt) {
    this.id = id;
    this.ticketId = ticketId;
    this.orgId = orgId;
    this.authorId = authorId;
    this.visibility = visibility;
    this.body = body;
    this.createdAt = createdAt;
  }

  public static TicketComment create(UUID ticketId, UUID orgId, UUID authorId, CommentVisibility visibility, String body) {
    if (ticketId == null) throw new IllegalArgumentException("ticketId is required");
    if (orgId == null) throw new IllegalArgumentException("orgId is required");
    if (authorId == null) throw new IllegalArgumentException("authorId is required");
    if (visibility == null) visibility = CommentVisibility.PUBLIC;
    if (body == null || body.isBlank()) throw new IllegalArgumentException("body is required");
    return new TicketComment(TicketCommentId.newId(), ticketId, orgId, authorId, visibility, body.trim(), Instant.now());
  }

  public static TicketComment rehydrate(TicketCommentId id, UUID ticketId, UUID orgId, UUID authorId,
                                       CommentVisibility visibility, String body, Instant createdAt) {
    return new TicketComment(id, ticketId, orgId, authorId, visibility, body, createdAt);
  }

  public TicketCommentId id() { return id; }
  public UUID ticketId() { return ticketId; }
  public UUID orgId() { return orgId; }
  public UUID authorId() { return authorId; }
  public CommentVisibility visibility() { return visibility; }
  public String body() { return body; }
  public Instant createdAt() { return createdAt; }
}
