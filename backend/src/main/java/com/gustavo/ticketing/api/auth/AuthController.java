package com.gustavo.ticketing.api.auth;

import com.gustavo.ticketing.api.auth.dto.LoginRequest;
import com.gustavo.ticketing.api.auth.dto.LoginResponse;
import com.gustavo.ticketing.application.auth.LoginUseCase;
import com.gustavo.ticketing.domain.auth.AuthPrincipal;
import com.gustavo.ticketing.infrastructure.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final LoginUseCase login;
  private final JwtService jwt;

  public AuthController(LoginUseCase login, JwtService jwt) {
    this.login = login;
    this.jwt = jwt;
  }

  @PostMapping("/login")
  public LoginResponse login(@Valid @RequestBody LoginRequest req) {
    AuthPrincipal principal = login.execute(req.email(), req.password());
    return new LoginResponse(jwt.createAccessToken(principal));
  }

  @GetMapping("/me")
  public Map<String, Object> me(Authentication authentication) {
    var principal = (AuthPrincipal) authentication.getPrincipal();
    return Map.of(
        "userId", principal.userId(),
        "orgId", principal.orgId(),
        "role", principal.role(),
        "email", principal.email()
    );
  }
}
