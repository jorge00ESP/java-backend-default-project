package com.jorge.jwt_user_service.infraestructure.adapter.in.web.dto;

public record AuthRequest(
        String username,
        String password
) {
}
