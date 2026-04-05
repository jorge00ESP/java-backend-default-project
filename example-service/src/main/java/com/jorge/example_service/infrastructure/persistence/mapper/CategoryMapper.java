package com.jorge.example_service.infrastructure.persistence.mapper;

import com.jorge.example_service.domain.model.Category;
import com.jorge.example_service.infrastructure.persistence.entity.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

  public Category toDomain(CategoryEntity categoryEntity) {
    return new Category(
            categoryEntity.getId(),
            categoryEntity.getName()
    );
  }

  public CategoryEntity toEntity(Category category) {
    CategoryEntity entity = new CategoryEntity();

    entity.setId(category.id());
    entity.setName(category.name());

    return entity;
  }
}
