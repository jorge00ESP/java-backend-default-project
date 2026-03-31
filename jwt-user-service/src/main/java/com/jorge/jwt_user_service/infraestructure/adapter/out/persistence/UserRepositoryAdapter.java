package com.jorge.jwt_user_service.infraestructure.adapter.out.persistence;

import com.jorge.jwt_user_service.domain.model.User;
import com.jorge.jwt_user_service.domain.ports.out.UserRepoPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryAdapter implements UserRepoPort {

  private final UserJpaRepository userJpaRepository;

  public UserRepositoryAdapter(UserJpaRepository userJpaRepository) {
    this.userJpaRepository = userJpaRepository;
  }


  @Override
  public Optional<User> findByUsername(String username) {
    return userJpaRepository.findByUsername(username).map(UserEntity::toDomain);
  }

  @Override
  public User save(User user) {
    UserEntity userEntity = UserEntity.fromDomain(user);
    UserEntity savedEntity = userJpaRepository.save(userEntity);

    return savedEntity.toDomain();
  }

  @Override
  public boolean existsByUsername(String username) {
    return userJpaRepository.existsByUsername(username);
  }
}
