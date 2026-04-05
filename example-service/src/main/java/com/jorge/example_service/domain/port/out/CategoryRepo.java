package com.jorge.example_service.domain.port.out;

import com.jorge.example_service.domain.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepo {
  Optional<Category> findById(Long id);
  List<Category> getAll();
  Category save(Category category);
}
