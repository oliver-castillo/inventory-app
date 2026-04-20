package com.app.inventory.domain.service.impl;

import com.app.inventory.config.AlreadyExistsException;
import com.app.inventory.config.ErrorMessage;
import com.app.inventory.domain.model.User;
import com.app.inventory.domain.service.UserService;
import com.app.inventory.mapper.UserMapper;
import com.app.inventory.persistence.entity.UserEntity;
import com.app.inventory.persistence.repository.JpaUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultUserService implements UserService {
  private final JpaUserRepository jpaUserRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public User createUser(User user) {
    validateEmail(user.getEmail());
    User userWithEncryptedPassword = encryptPassword(user);
    UserEntity userEntity = userMapper.userToUserEntity(userWithEncryptedPassword);
    UserEntity savedUser = jpaUserRepository.save(userEntity);
    return userMapper.userEntityToUser(savedUser);
  }

  private void validateEmail(String email) {
    if (jpaUserRepository.existsByEmail(email)) {
      log.warn("Attempt to create user with existing email: {}", email);
      throw new AlreadyExistsException(ErrorMessage.EMAIL_ALREADY_EXISTS);
    }
  }

  private User encryptPassword(User user) {
    String encryptedPassword = passwordEncoder.encode(user.getPassword());
    return new User(user.getFirstName(), user.getLastName(), user.getEmail(), encryptedPassword);
  }
}
