package com.jorge.jwt_user_service.infraestructure.rest.dto;

public record AuthRequest(
        String username,
        String password
) {
}
