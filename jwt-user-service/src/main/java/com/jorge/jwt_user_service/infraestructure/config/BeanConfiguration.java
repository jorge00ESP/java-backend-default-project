package com.jorge.jwt_user_service.infraestructure.config;

import com.jorge.jwt_user_service.application.service.AuthService;
import com.jorge.jwt_user_service.domain.ports.in.AuthServicePort;
import com.jorge.jwt_user_service.domain.ports.out.JwtRepoPort;
import com.jorge.jwt_user_service.domain.ports.out.PasswordEncoderRepoPort;
import com.jorge.jwt_user_service.domain.ports.out.UserRepoPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

  @Bean
  public AuthServicePort authServicePort(
          UserRepoPort userRepoPort,
          PasswordEncoderRepoPort passwordEncoderRepoPort,
          JwtRepoPort jwtRepoPort
  ){
    return new AuthService(userRepoPort, passwordEncoderRepoPort, jwtRepoPort);
  }
}
