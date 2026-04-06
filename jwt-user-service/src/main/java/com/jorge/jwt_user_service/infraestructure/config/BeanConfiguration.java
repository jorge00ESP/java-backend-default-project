package com.jorge.jwt_user_service.infraestructure.config;

import com.jorge.common.security.JwtUtils;
import com.jorge.jwt_user_service.domain.ports.out.PasswordEncoderRepo;
import com.jorge.jwt_user_service.domain.ports.out.UserRepo;
import com.jorge.jwt_user_service.domain.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

  @Bean
  public AuthService authService(
          UserRepo userRepo,
          PasswordEncoderRepo passwordEncoderRepo,
          JwtUtils jwtUtils
  ){
    return new AuthService(userRepo, passwordEncoderRepo, jwtUtils);
  }
}
