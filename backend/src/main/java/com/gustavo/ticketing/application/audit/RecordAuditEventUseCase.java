package com.gustavo.ticketing.application.audit;

import com.gustavo.ticketing.domain.audit.AuditEvent;
import com.gustavo.ticketing.domain.audit.AuditEventRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RecordAuditEventUseCase {
  private final AuditEventRepository repo;

  public RecordAuditEventUseCase(AuditEventRepository repo) {
    this.repo = repo;
  }

  public void execute(UUID orgId, UUID actorId, String entityType, UUID entityId, String action, String metadataJson) {
    var event = AuditEvent.create(orgId, actorId, entityType, entityId, action, metadataJson);
    repo.save(event);
  }
}
