package com.gustavo.ticketing.infrastructure.persistence.ticket;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tickets")
public class TicketJpaEntity {

  @Id
  @Column(columnDefinition = "uuid")
  private UUID id;

  @Column(name = "org_id", nullable = false, columnDefinition = "uuid")
  private UUID orgId;

  @Column(name = "created_by", nullable = false, columnDefinition = "uuid")
  private UUID createdBy;

  @Column(name = "assigned_to", columnDefinition = "uuid")
  private UUID assignedTo;

  @Column(nullable = false, length = 200)
  private String title;

  @Column(nullable = false, columnDefinition = "text")
  private String description;

  @Column(nullable = false, length = 30)
  private String status;

  @Column(nullable = false, length = 30)
  private String priority;

  @Column(columnDefinition = "text")
  private String tags;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt;

  @PrePersist
  void prePersist() {
    var now = Instant.now();
    if (createdAt == null) createdAt = now;
    updatedAt = now;
  }

  @PreUpdate
  void preUpdate() {
    updatedAt = Instant.now();
  }

  // getters/setters (puedes generar con IDE)
  public UUID getId() { return id; }
  public void setId(UUID id) { this.id = id; }
  public UUID getOrgId() { return orgId; }
  public void setOrgId(UUID orgId) { this.orgId = orgId; }
  public UUID getCreatedBy() { return createdBy; }
  public void setCreatedBy(UUID createdBy) { this.createdBy = createdBy; }
  public UUID getAssignedTo() { return assignedTo; }
  public void setAssignedTo(UUID assignedTo) { this.assignedTo = assignedTo; }
  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }
  public String getPriority() { return priority; }
  public void setPriority(String priority) { this.priority = priority; }
  public String getTags() { return tags; }
  public void setTags(String tags) { this.tags = tags; }
  public Instant getCreatedAt() { return createdAt; }
  public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
  public Instant getUpdatedAt() { return updatedAt; }
  public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}