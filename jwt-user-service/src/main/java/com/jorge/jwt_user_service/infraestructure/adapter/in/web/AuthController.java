package com.jorge.jwt_user_service.infraestructure.adapter.in.web;

import com.jorge.jwt_user_service.domain.model.User;
import com.jorge.jwt_user_service.domain.ports.in.AuthServicePort;
import com.jorge.jwt_user_service.infraestructure.adapter.in.web.dto.AuthRequest;
import com.jorge.jwt_user_service.infraestructure.adapter.in.web.dto.AuthResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthServicePort authServicePort;

  public AuthController(AuthServicePort authServicePort) {
    this.authServicePort = authServicePort;
  }

  @PostMapping("/register")
  public User register(@RequestBody AuthRequest authRequest) {
    User user = new User(null, authRequest.username(), authRequest.password(), null);
    return authServicePort.register(user);
  }

  @PostMapping("/login")
  public AuthResponse login(@RequestBody AuthRequest authRequest) {
    String token = authServicePort.login(authRequest.username(), authRequest.password());
    return new AuthResponse(token);
  }
}
