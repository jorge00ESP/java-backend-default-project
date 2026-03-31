package com.jorge.jwt_user_service.domain.ports.out;

public interface PasswordEncoderRepoPort {
  String encodePassword(String rawPassword);
  boolean isPairedPassword(String rawPassword, String encodedPassword);
}
