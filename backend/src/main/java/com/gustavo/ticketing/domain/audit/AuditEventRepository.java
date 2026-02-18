package com.gustavo.ticketing.domain.audit;

public interface AuditEventRepository {
  AuditEvent save(AuditEvent event);
}
