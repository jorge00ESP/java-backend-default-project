package com.jorge.example_service.infrastructure.rest.Controller;

import com.jorge.example_service.domain.model.Category;
import com.jorge.example_service.domain.model.Furniture;
import com.jorge.example_service.domain.port.in.CategoryUseCase;
import com.jorge.example_service.domain.port.in.FurnitureUseCase;
import com.jorge.example_service.infrastructure.rest.dto.CategoryDto;
import com.jorge.example_service.infrastructure.rest.dto.FurnitureDto;
import com.jorge.example_service.infrastructure.rest.dto.request.FurnitureDtoRequest;
import com.jorge.example_service.infrastructure.rest.dto.request.FurniturePriceDtoRequest;
import com.jorge.example_service.infrastructure.rest.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/furniture")
public class FurnitureController {

  private final FurnitureUseCase furnitureUseCase;
  private final CategoryUseCase categoryUseCase;

  public FurnitureController(FurnitureUseCase furnitureUseCase,  CategoryUseCase categoryUseCase) {
    this.furnitureUseCase = furnitureUseCase;
    this.categoryUseCase = categoryUseCase;
  }

  @GetMapping
  public ResponseEntity<?> getAll() {
    return ApiResponse.generate(
            HttpStatus.OK,
            "List of Furniture",
            furnitureUseCase.getAll().stream().map(f -> new FurnitureDto(
                    f.id(),
                    f.name(),
                    f.price(),
                    new CategoryDto(f.category().id(), f.category().name())
            )).toList()
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable Long id) {
    Furniture furniture = furnitureUseCase.getById(id);

    return ApiResponse.generate(
            HttpStatus.OK,
            "Furniture",
            furniture
    );
  }


  @PostMapping
  public ResponseEntity<?> create(@RequestBody FurnitureDtoRequest furnitureDtoRequest) {
    Category category = categoryUseCase.getById(furnitureDtoRequest.categoryId());

    Furniture furniture = new Furniture(
            null,
            furnitureDtoRequest.name(),
            furnitureDtoRequest.price(),
            category
    );

    Furniture savedFurniture = furnitureUseCase.create(furniture);
    FurnitureDto savedDto = new FurnitureDto(
            savedFurniture.id(),
            savedFurniture.name(),
            savedFurniture.price(),
            new CategoryDto(savedFurniture.category().id(), savedFurniture.name())
    );

    return ApiResponse.generate(
            HttpStatus.CREATED,
            "Created Furniture",
            savedDto
    );
  }

  @PutMapping("/price")
  public ResponseEntity<?> updatePrice(@RequestBody FurniturePriceDtoRequest furniturePriceDtoRequest) {
    Furniture updatedFurniture = furnitureUseCase.updatePrice(furniturePriceDtoRequest.id(), furniturePriceDtoRequest.price());
    FurnitureDto updatedDto = new FurnitureDto(
            updatedFurniture.id(),
            updatedFurniture.name(),
            updatedFurniture.price(),
            new CategoryDto(updatedFurniture.category().id(), updatedFurniture.category().name())
    );

    return ApiResponse.generate(
            HttpStatus.OK,
            "Updated Furniture Price",
            updatedDto
    );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    furnitureUseCase.delete(id);
    return ApiResponse.generate(
            HttpStatus.OK,
            "Deleted Furniture",
            null
    );
  }
}
