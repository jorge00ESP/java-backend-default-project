package com.jorge.jwt_user_service.domain.ports.out;

import com.jorge.jwt_user_service.domain.model.User;

public interface JwtRepoPort {
  String generateToken(User user);
  boolean validateToken(String token);
  String getUsernameFromToken(String token);
}
