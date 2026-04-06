package com.jorge.jwt_user_service.domain.service;

import com.jorge.common.security.JwtUtils;
import com.jorge.jwt_user_service.domain.exception.CustomException;
import com.jorge.jwt_user_service.domain.model.User;
import com.jorge.jwt_user_service.domain.ports.in.AuthUseCase;
import com.jorge.jwt_user_service.domain.ports.out.PasswordEncoderRepo;
import com.jorge.jwt_user_service.domain.ports.out.UserRepo;
import jakarta.ws.rs.core.Response;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;

public class AuthService implements AuthUseCase {

  private final UserRepo userRepo;
  private final PasswordEncoderRepo passwordEncoderRepo;
  private final JwtUtils jwtUtils;

  public AuthService(UserRepo userRepo, PasswordEncoderRepo passwordEncoderRepo, JwtUtils jwtUtils) {
    this.userRepo = userRepo;
    this.passwordEncoderRepo = passwordEncoderRepo;
    this.jwtUtils = jwtUtils;
  }

  @Override
  public String login(String username, String password) {
    User user = this.userRepo.findByUsername(username)
        .orElseThrow(() -> new CustomException("User not found", Response.Status.NOT_FOUND));

    if (!this.passwordEncoderRepo.isPairedPassword(password, user.getPassword())) {
      throw new CustomException("User's password is incorrect",  Response.Status.BAD_REQUEST);
    }

    List<SimpleGrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role)).toList();

    return this.jwtUtils.generateToken(user.getUsername(), authorities);
  }

  @Override
  public User register(User user) {
    if (this.userRepo.existsByUsername(user.getUsername())) {
      throw new CustomException("This username is already in use",  Response.Status.BAD_REQUEST);
    }

    String encodedPassword = this.passwordEncoderRepo.encodePassword(user.getPassword());
    user.setPassword(encodedPassword);

    if (user.getRoles() == null || user.getRoles().isEmpty()) {
      user.setRoles(Collections.singleton("ROLE_USER"));
    }

    return this.userRepo.save(user);
  }

}
