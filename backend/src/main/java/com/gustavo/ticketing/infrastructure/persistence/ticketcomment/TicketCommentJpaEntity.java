package com.gustavo.ticketing.infrastructure.persistence.ticketcomment;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "ticket_comments")
public class TicketCommentJpaEntity {

  @Id
  @Column(columnDefinition = "uuid")
  private UUID id;

  @Column(name = "ticket_id", nullable = false, columnDefinition = "uuid")
  private UUID ticketId;

  @Column(name = "org_id", nullable = false, columnDefinition = "uuid")
  private UUID orgId;

  @Column(name = "author_id", nullable = false, columnDefinition = "uuid")
  private UUID authorId;

  @Column(nullable = false, length = 20)
  private String visibility;

  @Column(nullable = false, columnDefinition = "text")
  private String body;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  @PrePersist
  void prePersist() {
    if (createdAt == null) createdAt = Instant.now();
  }

  public UUID getId() { return id; }
  public void setId(UUID id) { this.id = id; }
  public UUID getTicketId() { return ticketId; }
  public void setTicketId(UUID ticketId) { this.ticketId = ticketId; }
  public UUID getOrgId() { return orgId; }
  public void setOrgId(UUID orgId) { this.orgId = orgId; }
  public UUID getAuthorId() { return authorId; }
  public void setAuthorId(UUID authorId) { this.authorId = authorId; }
  public String getVisibility() { return visibility; }
  public void setVisibility(String visibility) { this.visibility = visibility; }
  public String getBody() { return body; }
  public void setBody(String body) { this.body = body; }
  public Instant getCreatedAt() { return createdAt; }
  public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
