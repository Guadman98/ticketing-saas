package com.gustavo.ticketing.application.auth;

import com.gustavo.ticketing.domain.auth.AuthPrincipal;
import com.gustavo.ticketing.infrastructure.persistence.user.UserJpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginUseCase {

  private final UserJpaRepository users;
  private final PasswordEncoder encoder;

  public LoginUseCase(UserJpaRepository users, PasswordEncoder encoder) {
    this.users = users;
    this.encoder = encoder;
  }

  public AuthPrincipal execute(String email, String password) {
    var user = users.findFirstByEmailIgnoreCase(email)
        .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

    if (!encoder.matches(password, user.getPasswordHash())) {
      throw new IllegalArgumentException("Invalid credentials");
    }

    return new AuthPrincipal(user.getId(), user.getOrgId(), user.getRole(), user.getEmail());
  }
}
