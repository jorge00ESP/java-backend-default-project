package com.jorge.example_service.domain.service;

import com.jorge.example_service.domain.model.Category;
import com.jorge.example_service.domain.port.in.CategoryUseCase;
import com.jorge.example_service.domain.port.out.CategoryRepo;

import java.util.List;

public class CategoryService implements CategoryUseCase {

  private final CategoryRepo categoryRepo;

  public CategoryService(CategoryRepo categoryRepo) {
    this.categoryRepo = categoryRepo;
  }

  @Override
  public Category getById(Long id) {
    return this.categoryRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Category not found"));
  }

  @Override
  public List<Category> getAll() {
    return this.categoryRepo.getAll();
  }

  @Override
  public Category create(Category category) {
    if (category.name() == null || category.name().isEmpty()) {
      throw new RuntimeException("Category name must not be empty");
    }

    return this.categoryRepo.save(category);
  }
}
