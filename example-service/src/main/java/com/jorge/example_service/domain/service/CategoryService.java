package com.jorge.example_service.domain.service;

import com.jorge.example_service.domain.exception.CustomException;
import com.jorge.example_service.domain.model.Category;
import com.jorge.example_service.domain.port.in.CategoryUseCase;
import com.jorge.example_service.domain.port.out.CategoryRepo;
import jakarta.ws.rs.core.Response;

import java.util.List;

public class CategoryService implements CategoryUseCase {

  private final CategoryRepo categoryRepo;

  public CategoryService(CategoryRepo categoryRepo) {
    this.categoryRepo = categoryRepo;
  }

  @Override
  public Category getById(Long id) {
    return this.categoryRepo.findById(id)
            .orElseThrow(() -> new CustomException("Category not found", Response.Status.NOT_FOUND));
  }

  @Override
  public List<Category> getAll() {
    return this.categoryRepo.getAll();
  }

  @Override
  public Category create(Category category) {
    if (category.name() == null || category.name().isEmpty()) {
      throw new CustomException("Category name must not be empty",  Response.Status.BAD_REQUEST);
    }

    return this.categoryRepo.save(category);
  }
}
