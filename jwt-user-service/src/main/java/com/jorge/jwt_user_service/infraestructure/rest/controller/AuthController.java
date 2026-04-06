package com.jorge.jwt_user_service.infraestructure.rest.controller;

import com.jorge.jwt_user_service.domain.model.User;
import com.jorge.jwt_user_service.domain.ports.in.AuthUseCase;
import com.jorge.jwt_user_service.infraestructure.rest.dto.AuthRequest;
import com.jorge.jwt_user_service.infraestructure.rest.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthUseCase authUseCase;

  public AuthController(AuthUseCase authUseCase) {
    this.authUseCase = authUseCase;
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody AuthRequest authRequest) {
    User user = new User(null, authRequest.username(), authRequest.password(), null);
    User userSave = authUseCase.register(user);

    return ApiResponse.generate(
            HttpStatus.OK,
            "User registered successfully",
            userSave.getUsername()
    );
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
    String token = authUseCase.login(authRequest.username(), authRequest.password());
    return ApiResponse.generate(
            HttpStatus.OK,
            "Login successful",
            token
    );
  }
}
