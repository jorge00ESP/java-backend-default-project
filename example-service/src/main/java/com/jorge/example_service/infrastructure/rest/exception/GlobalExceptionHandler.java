package com.jorge.example_service.infrastructure.rest.exception;

import com.jorge.example_service.domain.exception.CustomException;
import com.jorge.example_service.infrastructure.rest.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // Throw handler exceptions
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<?> handleDomainError(CustomException ex) {
    return ApiResponse.generate(
            HttpStatus.valueOf(ex.getStatusCode()),
            ex.getMessage(),
            null
    );
  }

  // Unexpected errors
  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleGeneralError(Exception ex) {
    return ApiResponse.generate(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Unexpected errors",
            null
    );
  }
}
