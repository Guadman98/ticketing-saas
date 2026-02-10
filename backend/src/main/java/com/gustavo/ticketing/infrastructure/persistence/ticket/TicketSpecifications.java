package com.gustavo.ticketing.infrastructure.persistence.ticket;

import com.gustavo.ticketing.application.ticket.TicketFilters;
import org.springframework.data.jpa.domain.Specification;

public final class TicketSpecifications {
  private TicketSpecifications() {}

  public static Specification<TicketJpaEntity> byOrgAndFilters(java.util.UUID orgId, TicketFilters f) {
    return (root, query, cb) -> {
      var p = cb.conjunction();

      p = cb.and(p, cb.equal(root.get("orgId"), orgId));

      if (f != null) {
        if (f.status() != null) {
          p = cb.and(p, cb.equal(root.get("status"), f.status().name()));
        }
        if (f.priority() != null) {
          p = cb.and(p, cb.equal(root.get("priority"), f.priority().name()));
        }
        if (f.assignedTo() != null) {
          p = cb.and(p, cb.equal(root.get("assignedTo"), f.assignedTo()));
        }
        if (f.q() != null && !f.q().isBlank()) {
          var like = "%" + f.q().toLowerCase() + "%";
          p = cb.and(p, cb.or(
              cb.like(cb.lower(root.get("title")), like),
              cb.like(cb.lower(root.get("description")), like)
          ));
        }
      }

      return p;
    };
  }
}