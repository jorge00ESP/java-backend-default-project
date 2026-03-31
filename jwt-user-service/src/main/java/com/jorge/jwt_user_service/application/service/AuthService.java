package com.jorge.jwt_user_service.application.service;

import com.jorge.jwt_user_service.domain.model.User;
import com.jorge.jwt_user_service.domain.ports.in.AuthServicePort;
import com.jorge.jwt_user_service.domain.ports.out.JwtRepoPort;
import com.jorge.jwt_user_service.domain.ports.out.PasswordEncoderRepoPort;
import com.jorge.jwt_user_service.domain.ports.out.UserRepoPort;

import java.util.Collections;

public class AuthService implements AuthServicePort {

  private final UserRepoPort userRepo;
  private final PasswordEncoderRepoPort passwordEncoderRepo;
  private final JwtRepoPort jwtRepo;

  public AuthService(UserRepoPort userRepo,PasswordEncoderRepoPort passwordEncoderRepo,JwtRepoPort jwtRepo) {
    this.userRepo = userRepo;
    this.passwordEncoderRepo = passwordEncoderRepo;
    this.jwtRepo = jwtRepo;
  }

  @Override
  public String login(String username, String password) {
    User user = this.userRepo.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("User not found"));

    if (!this.passwordEncoderRepo.isPairedPassword(password, user.getPassword())) {
      throw new RuntimeException("User's password is incorrect");
    }

    return this.jwtRepo.generateToken(user);
  }

  @Override
  public User register(User user) {
    if (this.userRepo.existsByUsername(user.getUsername())) {
      throw new RuntimeException("This username is already in use");
    }

    String encodedPassword = this.passwordEncoderRepo.encodePassword(user.getPassword());
    user.setPassword(encodedPassword);

    if (user.getRoles() == null || user.getRoles().isEmpty()) {
      user.setRoles(Collections.singleton("ROLE_USER"));
    }

    return this.userRepo.save(user);
  }

}
