package com.jorge.example_service.infrastructure.rest.Controller;

import com.jorge.example_service.domain.model.Category;
import com.jorge.example_service.domain.port.in.CategoryUseCase;
import com.jorge.example_service.infrastructure.rest.dto.CategoryDto;
import com.jorge.example_service.infrastructure.rest.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

  private final CategoryUseCase categoryUseCase;

  public CategoryController(CategoryUseCase categoryUseCase) {
    this.categoryUseCase = categoryUseCase;
  }

  @GetMapping
  public ResponseEntity<?> getAll() {
    return ApiResponse.generate(
            HttpStatus.OK,
            "List of Categories",
            categoryUseCase.getAll().stream().map(c -> new CategoryDto(
                    c.id(),
                    c.name()
            )).toList()
    );
  }

  @PostMapping
  public ResponseEntity<?> save(@RequestBody CategoryDto categoryDto) {
    Category category = new Category(null, categoryDto.name());
    Category savedCategory = categoryUseCase.create(category);
    CategoryDto savedDto = new CategoryDto(savedCategory.id(), savedCategory.name());

    return ApiResponse.generate(
            HttpStatus.CREATED,
            "Category created",
            savedDto
    );
  }
}
