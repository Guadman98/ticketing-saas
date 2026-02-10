package com.gustavo.ticketing.infrastructure.persistence.ticket;

import com.gustavo.ticketing.domain.ticket.*;

public final class TicketMapper {
    private TicketMapper() {
    }

    public static TicketJpaEntity toEntity(Ticket t) {
        var e = new TicketJpaEntity();
        e.setId(t.id().value());
        e.setOrgId(t.orgId());
        e.setCreatedBy(t.createdBy());
        e.setAssignedTo(t.assignedTo());
        e.setTitle(t.title());
        e.setDescription(t.description());
        e.setStatus(t.status().name());
        e.setPriority(t.priority().name());
        e.setTags(t.tags());
        e.setCreatedAt(t.createdAt());
        e.setUpdatedAt(t.updatedAt());
        return e;
    }

    public static Ticket toDomain(TicketJpaEntity e) {
        return Ticket.rehydrate(
                new TicketId(e.getId()),
                e.getOrgId(),
                e.getCreatedBy(),
                e.getAssignedTo(),
                e.getTitle(),
                e.getDescription(),
                TicketStatus.valueOf(e.getStatus()),
                TicketPriority.valueOf(e.getPriority()),
                e.getTags(),
                e.getCreatedAt(),
                e.getUpdatedAt());
    }
}