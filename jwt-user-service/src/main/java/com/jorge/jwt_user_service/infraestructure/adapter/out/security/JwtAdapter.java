package com.jorge.jwt_user_service.infraestructure.adapter.out.security;

import com.jorge.jwt_user_service.domain.model.User;
import com.jorge.jwt_user_service.domain.ports.out.JwtRepoPort;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtAdapter implements JwtRepoPort {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private long expiration;

  private SecretKey getSigningKey() {
    byte[] keyBytes = this.secret.getBytes(StandardCharsets.UTF_8);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  @Override
  public String generateToken(User user) {
    return Jwts.builder()
            .subject(user.getUsername())
            .claim("roles", user.getRoles())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + this.expiration))
            .signWith(getSigningKey())
            .compact();
  }

  @Override
  public boolean validateToken(String token) {
    try {
      Jwts.parser().verifyWith(getSigningKey())
              .build()
              .parseSignedClaims(token);

      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public String getUsernameFromToken(String token) {
    return Jwts.parser().verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
  }
}
