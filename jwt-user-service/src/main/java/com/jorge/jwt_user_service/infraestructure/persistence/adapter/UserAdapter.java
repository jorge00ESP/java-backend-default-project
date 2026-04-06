package com.jorge.jwt_user_service.infraestructure.persistence.adapter;

import com.jorge.jwt_user_service.domain.model.User;
import com.jorge.jwt_user_service.domain.ports.out.UserRepo;
import com.jorge.jwt_user_service.infraestructure.persistence.repository.UserJpaRepository;
import com.jorge.jwt_user_service.infraestructure.persistence.entity.UserEntity;
import com.jorge.jwt_user_service.infraestructure.persistence.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserAdapter implements UserRepo {

  private final UserJpaRepository userJpaRepository;
  private final UserMapper userMapper;

  public UserAdapter(UserJpaRepository userJpaRepository) {
    this.userJpaRepository = userJpaRepository;
    this.userMapper = new UserMapper();
  }


  @Override
  public Optional<User> findByUsername(String username) {
    return userJpaRepository.findByUsername(username).map(userMapper::toDomain);
  }

  @Override
  public User save(User user) {
    UserEntity userEntity = this.userMapper.toEntity(user);
    UserEntity savedEntity = userJpaRepository.save(userEntity);

    return this.userMapper.toDomain(savedEntity);
  }

  @Override
  public boolean existsByUsername(String username) {
    return userJpaRepository.existsByUsername(username);
  }
}
