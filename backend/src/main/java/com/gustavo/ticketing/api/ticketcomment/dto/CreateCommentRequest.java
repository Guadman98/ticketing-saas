package com.gustavo.ticketing.api.ticketcomment.dto;

import com.gustavo.ticketing.domain.ticketcomment.CommentVisibility;
import jakarta.validation.constraints.NotBlank;

public record CreateCommentRequest(
    CommentVisibility visibility,
    @NotBlank String body
) {}
