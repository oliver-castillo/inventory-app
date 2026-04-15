package com.app.inventory.domain.service.impl;

import com.app.inventory.config.AlreadyExistsException;
import com.app.inventory.config.ErrorMessage;
import com.app.inventory.domain.model.User;
import com.app.inventory.domain.service.UserService;
import com.app.inventory.mapper.UserMapper;
import com.app.inventory.persistence.entity.UserEntity;
import com.app.inventory.persistence.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {
  private final JpaUserRepository jpaUserRepository;
  private final UserMapper userMapper;

  @Override
  public User createUser(User user) {
    validateEmail(user.getEmail());
    UserEntity savedUser = jpaUserRepository.save(userMapper.userToUserEntity(user));
    return userMapper.userEntityToUser(savedUser);
  }

  private void validateEmail(String email) {
    if (jpaUserRepository.existsByEmail(email)) {
      throw new AlreadyExistsException(ErrorMessage.EMAIL_ALREADY_EXISTS);
    }
  }
}
