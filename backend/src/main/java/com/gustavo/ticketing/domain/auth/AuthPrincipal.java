package com.gustavo.ticketing.domain.auth;

import java.util.UUID;

public record AuthPrincipal(
    UUID userId,
    UUID orgId,
    String role,
    String email
) {}
