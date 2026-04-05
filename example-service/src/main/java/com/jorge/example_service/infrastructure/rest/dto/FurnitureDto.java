package com.jorge.example_service.infrastructure.rest.dto;

public record FurnitureDto(
        Long id,
        String name,
        Double price,
        CategoryDto category
) {
}
