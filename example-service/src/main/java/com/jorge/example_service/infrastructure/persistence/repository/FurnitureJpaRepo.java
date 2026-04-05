package com.jorge.example_service.infrastructure.persistence.repository;

import com.jorge.example_service.infrastructure.persistence.entity.FurnitureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FurnitureJpaRepo extends JpaRepository<FurnitureEntity, Long> {
  List<FurnitureEntity> findByCategoryId(Long categoryId);
}
