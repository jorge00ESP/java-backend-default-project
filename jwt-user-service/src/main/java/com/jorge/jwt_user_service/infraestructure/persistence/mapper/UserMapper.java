package com.jorge.jwt_user_service.infraestructure.persistence.mapper;

import com.jorge.jwt_user_service.domain.model.User;
import com.jorge.jwt_user_service.infraestructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public User toDomain(UserEntity userEntity) {
    return new User(
            userEntity.getId(),
            userEntity.getUsername(),
            userEntity.getPassword(),
            userEntity.getRoles()
    );
  }

  public UserEntity toEntity(User user) {
    UserEntity userEntity = new UserEntity();

    userEntity.setId(user.getId());
    userEntity.setUsername(user.getUsername());
    userEntity.setPassword(user.getPassword());
    userEntity.setRoles(user.getRoles());

    return userEntity;
  }
}
