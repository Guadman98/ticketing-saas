package com.gustavo.ticketing.infrastructure.persistence.audit;

import com.gustavo.ticketing.domain.audit.AuditEvent;

public final class AuditEventMapper {
  private AuditEventMapper() {
  }

  public static AuditEventJpaEntity toEntity(AuditEvent event) {
    var e = new AuditEventJpaEntity();
    e.setId(event.id());
    e.setOrgId(event.orgId());
    e.setActorId(event.actorId());
    e.setEntityType(event.entityType());
    e.setEntityId(event.entityId());
    e.setAction(event.action());
    e.setMetadataJson(event.metadataJson());
    e.setCreatedAt(event.createdAt());
    return e;
  }

  public static AuditEvent toDomain(AuditEventJpaEntity e) {
    return AuditEvent.rehydrate(
        e.getId(),
        e.getOrgId(),
        e.getActorId(),
        e.getEntityType(),
        e.getEntityId(),
        e.getAction(),
        e.getMetadataJson(),
        e.getCreatedAt());
  }
}
