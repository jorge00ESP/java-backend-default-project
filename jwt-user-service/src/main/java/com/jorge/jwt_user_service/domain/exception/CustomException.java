package com.jorge.jwt_user_service.domain.exception;

import jakarta.ws.rs.core.Response;

public class CustomException extends RuntimeException {

  private final Response.Status status;

  public CustomException(String message, Response.Status status) {
    super(message);
    this.status = status;
  }

  public Response.Status getStatus() {
    return this.status;
  }

  public int getStatusCode() {
    return this.status.getStatusCode();
  }
}
