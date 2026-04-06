package com.jorge.example_service.domain.service;

import com.jorge.example_service.domain.exception.CustomException;
import com.jorge.example_service.domain.model.Category;
import com.jorge.example_service.domain.port.in.CategoryUseCase;
import com.jorge.example_service.domain.port.out.CategoryRepo;
import org.springframework.http.HttpStatus;

import java.util.List;

public class CategoryService implements CategoryUseCase {

  private final CategoryRepo categoryRepo;

  public CategoryService(CategoryRepo categoryRepo) {
    this.categoryRepo = categoryRepo;
  }

  @Override
  public Category getById(Long id) {
    return this.categoryRepo.findById(id)
            .orElseThrow(() -> new CustomException("Category not found", HttpStatus.NOT_FOUND.value()));
  }

  @Override
  public List<Category> getAll() {
    return this.categoryRepo.getAll();
  }

  @Override
  public Category create(Category category) {
    if (category.name() == null || category.name().isEmpty()) {
      throw new CustomException("Category name must not be empty",  HttpStatus.BAD_REQUEST.value());
    }

    return this.categoryRepo.save(category);
  }
}
