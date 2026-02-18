package com.gustavo.ticketing.infrastructure.persistence.audit;

import com.gustavo.ticketing.domain.audit.AuditEvent;
import com.gustavo.ticketing.domain.audit.AuditEventRepository;
import org.springframework.stereotype.Repository;

@Repository
public class AuditEventRepositoryAdapter implements AuditEventRepository {

  private final AuditEventJpaRepository jpa;

  public AuditEventRepositoryAdapter(AuditEventJpaRepository jpa) {
    this.jpa = jpa;
  }

  @Override
  public AuditEvent save(AuditEvent event) {
    var saved = jpa.save(AuditEventMapper.toEntity(event));
    return AuditEventMapper.toDomain(saved);
  }
}
