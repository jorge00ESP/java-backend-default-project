package com.jorge.example_service.domain.port.out;

import com.jorge.example_service.domain.model.Furniture;

import java.util.List;
import java.util.Optional;

public interface FurnitureRepo {
  Optional<Furniture> findById(Long id);
  List<Furniture> findAll();
  List<Furniture> getByCategory(Long category);
  Furniture save(Furniture furniture);
  void deleteById(Long id);

}
