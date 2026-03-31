package com.jorge.jwt_user_service.infraestructure.adapter.out.security;

import com.jorge.jwt_user_service.domain.ports.out.PasswordEncoderRepoPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptAdapter implements PasswordEncoderRepoPort {

  private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

  @Override
  public String encodePassword(String rawPassword) {
    return this.bCryptPasswordEncoder.encode(rawPassword);
  }

  @Override
  public boolean isPairedPassword(String rawPassword, String encodedPassword) {
    return this.bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
  }
}
