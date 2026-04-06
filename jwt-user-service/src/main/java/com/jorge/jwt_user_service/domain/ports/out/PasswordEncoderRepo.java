package com.jorge.jwt_user_service.domain.ports.out;

public interface PasswordEncoderRepo {
  String encodePassword(String rawPassword);
  boolean isPairedPassword(String rawPassword, String encodedPassword);
}
