package com.jorge.example_service.domain.service;

import com.jorge.example_service.domain.exception.CustomException;
import com.jorge.example_service.domain.model.Furniture;
import com.jorge.example_service.domain.port.in.FurnitureUseCase;
import com.jorge.example_service.domain.port.out.CategoryRepo;
import com.jorge.example_service.domain.port.out.FurnitureRepo;
import jakarta.ws.rs.core.Response;

import java.util.List;

public class FurnitureService implements FurnitureUseCase {

  private final FurnitureRepo furnitureRepo;
  private final CategoryRepo categoryRepo;

  public FurnitureService(FurnitureRepo furnitureRepo, CategoryRepo categoryRepo) {
    this.furnitureRepo = furnitureRepo;
    this.categoryRepo = categoryRepo;
  }

  @Override
  public Furniture getById(Long id) {
    return this.furnitureRepo.findById(id)
            .orElseThrow(() -> new CustomException("Furniture not found", Response.Status.NOT_FOUND));
  }

  @Override
  public List<Furniture> getAll() {
    return this.furnitureRepo.findAll();
  }

  @Override
  public Furniture create(Furniture furniture) {
    if(furniture.name() == null || furniture.name().isEmpty()) {
      throw new RuntimeException("Furniture name must not be empty");
    }

    if (furniture.price() <= 0) {
      throw new CustomException("Price must be greater than 0 or not null",  Response.Status.BAD_REQUEST);
    }

    this.categoryRepo.findById(furniture.category().id())
            .orElseThrow(() -> new CustomException("Category not found", Response.Status.NOT_FOUND));

    return this.furnitureRepo.save(furniture);
  }

  @Override
  public Furniture updatePrice(Long id, Double price) {
    Furniture furniture = this.furnitureRepo.findById(id)
            .orElseThrow(() -> new CustomException("Furniture not found", Response.Status.NOT_FOUND));

    if (price <= 0 || price.equals(furniture.price())) {
      throw new CustomException("Price must be greater than 0, different from the current price or not null",  Response.Status.BAD_REQUEST);
    }

    Furniture updateFurniture = new Furniture(
            furniture.id(),
            furniture.name(),
            price,
            furniture.category()
    );

    return this.furnitureRepo.save(updateFurniture);
  }

  @Override
  public void delete(Long id) {
    this.furnitureRepo.findById(id)
            .orElseThrow(() -> new CustomException("Furniture not found", Response.Status.NOT_FOUND));

    this.furnitureRepo.deleteById(id);
  }
}
