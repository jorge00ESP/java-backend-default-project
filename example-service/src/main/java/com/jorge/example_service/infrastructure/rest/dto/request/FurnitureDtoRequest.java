package com.jorge.example_service.infrastructure.rest.dto.request;

import com.jorge.example_service.infrastructure.rest.dto.CategoryDto;

public record FurnitureDtoRequest(
        String name,
        Double price,
        Long categoryId
) {
}
