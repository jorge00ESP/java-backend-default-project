package com.jorge.example_service.infrastructure.persistence.adpater;

import com.jorge.example_service.domain.model.Category;
import com.jorge.example_service.domain.port.out.CategoryRepo;
import com.jorge.example_service.infrastructure.persistence.entity.CategoryEntity;
import com.jorge.example_service.infrastructure.persistence.mapper.CategoryMapper;
import com.jorge.example_service.infrastructure.persistence.repository.CategoryJpaRepo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CategoryAdapter implements CategoryRepo {

  private final CategoryJpaRepo categoryJpaRepo;
  private final CategoryMapper categoryMapper;

  public CategoryAdapter(CategoryJpaRepo categoryJpaRepo, CategoryMapper categoryMapper) {
    this.categoryJpaRepo = categoryJpaRepo;
    this.categoryMapper = categoryMapper;
  }

  @Override
  public Optional<Category> findById(Long id) {
    return this.categoryJpaRepo.findById(id).map(categoryMapper::toDomain);
  }

  @Override
  public List<Category> getAll() {
    return this.categoryJpaRepo.findAll().stream().map(categoryMapper::toDomain).toList();
  }

  @Override
  public Category save(Category category) {
    CategoryEntity categoryEntity = categoryMapper.toEntity(category);
    CategoryEntity savedEntity = categoryJpaRepo.save(categoryEntity);

    return categoryMapper.toDomain(savedEntity);
  }
}
