package com.jorge.example_service.infrastructure.persistence.repository;

import com.jorge.example_service.infrastructure.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepo extends JpaRepository<CategoryEntity, Long> {
}
