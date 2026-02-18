package com.gustavo.ticketing.domain.audit;

import java.time.Instant;
import java.util.UUID;

public class AuditEvent {
  private final UUID id;
  private final UUID orgId;
  private final UUID actorId;
  private final String entityType;
  private final UUID entityId;
  private final String action;
  private final String metadataJson;
  private final Instant createdAt;

  private AuditEvent(UUID id, UUID orgId, UUID actorId, String entityType, UUID entityId, String action,
      String metadataJson, Instant createdAt) {
    this.id = id;
    this.orgId = orgId;
    this.actorId = actorId;
    this.entityType = entityType;
    this.entityId = entityId;
    this.action = action;
    this.metadataJson = metadataJson;
    this.createdAt = createdAt;
  }

  public static AuditEvent create(UUID orgId, UUID actorId, String entityType, UUID entityId, String action,
      String metadataJson) {
    if (orgId == null)
      throw new IllegalArgumentException("orgId is required");
    if (entityType == null || entityType.isBlank())
      throw new IllegalArgumentException("entityType is required");
    if (entityId == null)
      throw new IllegalArgumentException("entityId is required");
    if (action == null || action.isBlank())
      throw new IllegalArgumentException("action is required");

    return new AuditEvent(
        UUID.randomUUID(),
        orgId,
        actorId,
        entityType.trim(),
        entityId,
        action.trim(),
        metadataJson,
        Instant.now());
  }

  public static AuditEvent rehydrate(UUID id, UUID orgId, UUID actorId, String entityType, UUID entityId, String action,
      String metadataJson, Instant createdAt) {
    return new AuditEvent(id, orgId, actorId, entityType, entityId, action, metadataJson, createdAt);
  }

  public UUID id() {
    return id;
  }

  public UUID orgId() {
    return orgId;
  }

  public UUID actorId() {
    return actorId;
  }

  public String entityType() {
    return entityType;
  }

  public UUID entityId() {
    return entityId;
  }

  public String action() {
    return action;
  }

  public String metadataJson() {
    return metadataJson;
  }

  public Instant createdAt() {
    return createdAt;
  }
}
