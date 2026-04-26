package com.app.inventory.domain.service.impl;

import com.app.inventory.config.AlreadyExistsException;
import com.app.inventory.config.ErrorMessage;
import com.app.inventory.config.NotFoundException;
import com.app.inventory.domain.model.User;
import com.app.inventory.domain.repository.UserRepository;
import com.app.inventory.domain.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultUserService implements UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public User createUser(User user) {
    validateEmail(user.getEmail(), null);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  @Override
  @Transactional
  public User updateUser(Long userId, User user) {
    validateId(userId);
    validateEmail(user.getEmail(), userId);
    return userRepository.updateFields(userId, user.getFirstName(), user.getLastName(), user.getEmail());
  }

  private void validateEmail(String email, Long userId) {
    boolean exists = userId == null
        ? userRepository.existsByEmail(email)
        : userRepository.existsByEmailAndIdNot(email, userId);
    if (exists) {
      throw new AlreadyExistsException(ErrorMessage.EMAIL_ALREADY_EXISTS);
    }
  }

  private void validateId(Long id) {
    if (!userRepository.existsById(id)) {
      throw new NotFoundException(ErrorMessage.USER_NOT_FOUND);
    }
  }
}
