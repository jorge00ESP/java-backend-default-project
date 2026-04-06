package com.jorge.example_service.domain.exception;

import jakarta.ws.rs.core.Response.Status;

public class CustomException extends RuntimeException {

  private final Status status;

  public CustomException(String message, Status status) {
    super(message);
    this.status = status;
  }

  public Status getStatus() {
    return this.status;
  }

  public int getStatusCode() {
    return this.status.getStatusCode();
  }
}
