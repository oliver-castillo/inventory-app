package com.app.inventory.domain.service;

import com.app.inventory.domain.model.User;

public interface UserService {
  User createUser(User user);

  User updateUser(Long userId, User user);
}
