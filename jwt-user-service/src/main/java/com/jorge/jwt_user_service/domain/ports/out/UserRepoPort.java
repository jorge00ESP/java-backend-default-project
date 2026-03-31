package com.jorge.jwt_user_service.domain.ports.out;

import com.jorge.jwt_user_service.domain.model.User;

import java.util.Optional;

public interface UserRepoPort {
  Optional<User> findByUsername(String username);
  User save(User user);
  boolean existsByUsername(String username);
}
