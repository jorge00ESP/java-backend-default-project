package com.jorge.example_service.infrastructure.rest.exception;

import com.jorge.example_service.infrastructure.rest.util.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException e) {
    return ApiResponse.generate(
            HttpStatus.NOT_FOUND,
            e.getMessage(),
            null
    );
  }
}
