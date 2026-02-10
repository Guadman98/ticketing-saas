package com.gustavo.ticketing.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.UUID;

@ConfigurationProperties(prefix = "app.bootstrap")
public record BootstrapProperties(UUID orgId, UUID adminUserId) {}