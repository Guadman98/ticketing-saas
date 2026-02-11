package com.gustavo.ticketing.infrastructure.security;

import com.gustavo.ticketing.domain.auth.AuthPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private final JwtProperties props;
    private final SecretKey key;

    public JwtService(JwtProperties props) {
        this.props = props;
        this.key = Keys.hmacShaKeyFor(props.secret().getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(AuthPrincipal principal) {
        var now = Instant.now();
        var exp = now.plusSeconds(props.accessTokenMinutes() * 60);

        return Jwts.builder()
                .issuer(props.issuer())
                .subject(principal.userId().toString())
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .claim("orgId", principal.orgId().toString())
                .claim("role", principal.role())
                .claim("email", principal.email())
                .signWith(key)
                .compact();
    }

    public AuthPrincipal parse(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        UUID userId = UUID.fromString(claims.getSubject());
        UUID orgId = UUID.fromString(String.valueOf(claims.get("orgId")));
        String role = String.valueOf(claims.get("role"));
        String email = String.valueOf(claims.get("email"));

        return new AuthPrincipal(userId, orgId, role, email);
    }
}
