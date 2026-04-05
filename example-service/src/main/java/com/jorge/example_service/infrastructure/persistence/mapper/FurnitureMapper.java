package com.jorge.example_service.infrastructure.persistence.mapper;

import com.jorge.example_service.domain.model.Category;
import com.jorge.example_service.domain.model.Furniture;
import com.jorge.example_service.infrastructure.persistence.entity.CategoryEntity;
import com.jorge.example_service.infrastructure.persistence.entity.FurnitureEntity;
import org.springframework.stereotype.Component;

@Component
public class FurnitureMapper {

  public Furniture toDomain(FurnitureEntity entity) {
    return new Furniture(
            entity.getId(),
            entity.getName(),
            entity.getPrice(),
            new Category(entity.getCategory().getId(), entity.getCategory().getName())
    );
  }

  public FurnitureEntity toEntity(Furniture furniture) {
    FurnitureEntity furnitureEntity = new FurnitureEntity();
    CategoryEntity categoryEntity = new CategoryEntity();

    furnitureEntity.setId(furniture.id());
    furnitureEntity.setName(furniture.name());
    furnitureEntity.setPrice(furniture.price());

    categoryEntity.setId(furniture.category().id());
    categoryEntity.setName(furniture.category().name());

    furnitureEntity.setCategory(categoryEntity);

    return furnitureEntity;
  }
}
