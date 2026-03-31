package com.jorge.jwt_user_service.infraestructure.adapter.out.persistence;

import com.jorge.jwt_user_service.domain.model.User;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String username;

  private String password;

  @ElementCollection(fetch = FetchType.EAGER)
  private Set<String> roles;

  public User toDomain() {
    return new User(this.id, this.username, this.password, this.roles);
  }

  public static UserEntity fromDomain(User user) {
    UserEntity userEntity = new UserEntity();

    userEntity.id = user.getId();
    userEntity.username = user.getUsername();
    userEntity.password = user.getPassword();
    userEntity.roles = user.getRoles();

    return userEntity;
  }
}
