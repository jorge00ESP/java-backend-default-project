package com.jorge.jwt_user_service.domain.ports.in;

import com.jorge.jwt_user_service.domain.model.User;

public interface AuthServicePort {
  String login(String username, String password);
  User register(User user);
}
