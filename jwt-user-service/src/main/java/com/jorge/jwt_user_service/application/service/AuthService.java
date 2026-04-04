package com.jorge.jwt_user_service.application.service;

import com.jorge.common.security.JwtUtils;
import com.jorge.jwt_user_service.domain.model.User;
import com.jorge.jwt_user_service.domain.ports.in.AuthServicePort;
import com.jorge.jwt_user_service.domain.ports.out.PasswordEncoderRepoPort;
import com.jorge.jwt_user_service.domain.ports.out.UserRepoPort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;

public class AuthService implements AuthServicePort {

  private final UserRepoPort userRepo;
  private final PasswordEncoderRepoPort passwordEncoderRepo;
  private final JwtUtils jwtUtils;

  public AuthService(UserRepoPort userRepo,PasswordEncoderRepoPort passwordEncoderRepo, JwtUtils jwtUtils) {
    this.userRepo = userRepo;
    this.passwordEncoderRepo = passwordEncoderRepo;
    this.jwtUtils = jwtUtils;
  }

  @Override
  public String login(String username, String password) {
    User user = this.userRepo.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("User not found"));

    if (!this.passwordEncoderRepo.isPairedPassword(password, user.getPassword())) {
      throw new RuntimeException("User's password is incorrect");
    }

    List<SimpleGrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role)).toList();

    return this.jwtUtils.generateToken(user.getUsername(), authorities);
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
