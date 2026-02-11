package com.gustavo.ticketing.infrastructure.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, UUID> {
  Optional<UserJpaEntity> findFirstByEmailIgnoreCase(String email);
}
