package com.jorge.example_service.domain.port.in;

import com.jorge.example_service.domain.model.Furniture;

import java.util.List;

public interface FurnitureUseCase {
  Furniture getById(Long id);
  List<Furniture> getAll();
  Furniture create(Furniture furniture);
  Furniture updatePrice(Long id, Double price);
  void delete(Long id);
}
