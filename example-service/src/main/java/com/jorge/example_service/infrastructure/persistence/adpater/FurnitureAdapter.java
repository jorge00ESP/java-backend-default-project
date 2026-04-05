package com.jorge.example_service.infrastructure.persistence.adpater;

import com.jorge.example_service.domain.model.Furniture;
import com.jorge.example_service.domain.port.out.FurnitureRepo;
import com.jorge.example_service.infrastructure.persistence.entity.FurnitureEntity;
import com.jorge.example_service.infrastructure.persistence.mapper.CategoryMapper;
import com.jorge.example_service.infrastructure.persistence.mapper.FurnitureMapper;
import com.jorge.example_service.infrastructure.persistence.repository.CategoryJpaRepo;
import com.jorge.example_service.infrastructure.persistence.repository.FurnitureJpaRepo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class FurnitureAdapter implements FurnitureRepo {

  private final FurnitureJpaRepo furnitureJpaRepo;
  private final FurnitureMapper furnitureMapper;

  public FurnitureAdapter(FurnitureJpaRepo furnitureJpaRepo, FurnitureMapper furnitureMapper) {
    this.furnitureJpaRepo = furnitureJpaRepo;
    this.furnitureMapper = furnitureMapper;
  }


  @Override
  public Optional<Furniture> findById(Long id) {
    return furnitureJpaRepo.findById(id).map(furnitureMapper::toDomain);
  }

  @Override
  public List<Furniture> findAll() {
    return furnitureJpaRepo.findAll().stream().map(furnitureMapper::toDomain).toList();
  }

  @Override
  public List<Furniture> getByCategory(Long categoryId) {
    return furnitureJpaRepo.findByCategoryId(categoryId).stream().map(furnitureMapper::toDomain).toList();
  }

  @Override
  public Furniture save(Furniture furniture) {
    FurnitureEntity furnitureEntity = furnitureMapper.toEntity(furniture);
    FurnitureEntity savedEntity = furnitureJpaRepo.save(furnitureEntity);

    return furnitureMapper.toDomain(savedEntity);
  }

  @Override
  public void deleteById(Long id) {
    furnitureJpaRepo.deleteById(id);
  }
}
