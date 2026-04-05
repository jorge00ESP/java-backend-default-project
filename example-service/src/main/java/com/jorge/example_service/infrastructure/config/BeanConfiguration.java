package com.jorge.example_service.infrastructure.config;

import com.jorge.example_service.domain.port.out.CategoryRepo;
import com.jorge.example_service.domain.port.out.FurnitureRepo;
import com.jorge.example_service.domain.service.CategoryService;
import com.jorge.example_service.domain.service.FurnitureService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

  @Bean
  public FurnitureService furnitureService(
          FurnitureRepo furnitureRepo,
          CategoryRepo categoryRepo
  ) {
    return new FurnitureService(furnitureRepo, categoryRepo);
  }

  @Bean
  public CategoryService categoryService(
          CategoryRepo categoryRepo
  ) {
    return new CategoryService(categoryRepo);
  }
}
