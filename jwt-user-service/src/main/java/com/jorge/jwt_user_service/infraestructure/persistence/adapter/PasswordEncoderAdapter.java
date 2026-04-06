package com.jorge.jwt_user_service.infraestructure.persistence.adapter;

import com.jorge.jwt_user_service.domain.ports.out.PasswordEncoderRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderAdapter implements PasswordEncoderRepo {

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
