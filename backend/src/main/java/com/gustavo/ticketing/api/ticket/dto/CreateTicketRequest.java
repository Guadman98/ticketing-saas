package com.gustavo.ticketing.api.ticket.dto;

import com.gustavo.ticketing.domain.ticket.TicketPriority;
import jakarta.validation.constraints.NotBlank;

public record CreateTicketRequest(
    @NotBlank String title,
    @NotBlank String description,
    TicketPriority priority,
    String tags
) {}