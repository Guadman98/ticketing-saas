package com.gustavo.ticketing.domain.ticket;

import java.time.Instant;
import java.util.UUID;

public class Ticket {
    private final TicketId id;
    private final UUID orgId;
    private final UUID createdBy;
    private UUID assignedTo;

    private String title;
    private String description;
    private TicketStatus status;
    private TicketPriority priority;
    private String tags;

    private final Instant createdAt;
    private Instant updatedAt;

    private Ticket(
            TicketId id, UUID orgId, UUID createdBy,
            String title, String description,
            TicketStatus status, TicketPriority priority,
            String tags, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.orgId = orgId;
        this.createdBy = createdBy;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.tags = tags;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Ticket openNew(UUID orgId, UUID createdBy, String title, String description, TicketPriority priority,
            String tags) {
        var now = Instant.now();
        if (title == null || title.isBlank())
            throw new IllegalArgumentException("title is required");
        if (description == null || description.isBlank())
            throw new IllegalArgumentException("description is required");
        return new Ticket(
                TicketId.newId(),
                orgId,
                createdBy,
                title.trim(),
                description.trim(),
                TicketStatus.OPEN,
                priority == null ? TicketPriority.MEDIUM : priority,
                tags,
                now,
                now);
    }

    public static Ticket rehydrate(
            TicketId id, UUID orgId, UUID createdBy, UUID assignedTo,
            String title, String description, TicketStatus status, TicketPriority priority,
            String tags, Instant createdAt, Instant updatedAt) {
        var t = new Ticket(id, orgId, createdBy, title, description, status, priority, tags, createdAt, updatedAt);
        t.assignedTo = assignedTo;
        return t;
    }

    // Getters
    public TicketId id() {
        return id;
    }

    public UUID orgId() {
        return orgId;
    }

    public UUID createdBy() {
        return createdBy;
    }

    public UUID assignedTo() {
        return assignedTo;
    }

    public String title() {
        return title;
    }

    public String description() {
        return description;
    }

    public TicketStatus status() {
        return status;
    }

    public TicketPriority priority() {
        return priority;
    }

    public String tags() {
        return tags;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }
}