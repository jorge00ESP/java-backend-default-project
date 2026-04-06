package com.jorge.jwt_user_service.infraestructure.persistence.repository;

import com.jorge.jwt_user_service.infraestructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

  Optional<UserEntity> findByUsername(String username);
  boolean existsByUsername(String username);

}
