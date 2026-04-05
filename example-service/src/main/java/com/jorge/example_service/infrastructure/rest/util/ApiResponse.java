package com.jorge.example_service.infrastructure.rest.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public record ApiResponse<T>(
  int status,
  String message,
  T body
) {
  public static <T> ResponseEntity<ApiResponse<T>> generate(HttpStatus status, String message, T body) {
    ApiResponse<T> response = new ApiResponse<>(status.value(), message, body);
    return new ResponseEntity<>(response, status);
  }
}
