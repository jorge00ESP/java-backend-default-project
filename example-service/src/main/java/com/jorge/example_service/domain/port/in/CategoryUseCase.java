package com.jorge.example_service.domain.port.in;

import com.jorge.example_service.domain.model.Category;

import java.util.List;

public interface CategoryUseCase {
  Category getById(Long id);
  List<Category> getAll();
  Category create(Category category);
}
