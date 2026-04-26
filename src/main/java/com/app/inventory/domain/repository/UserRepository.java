package com.app.inventory.domain.repository;

import com.app.inventory.domain.model.User;

public interface UserRepository {
  User save(User user);

  User updateFields(Long id, String firstName, String lastName, String email);

  User findById(Long id);

  boolean existsById(Long id);

  boolean existsByEmail(String email);

  boolean existsByEmailAndIdNot(String email, Long id);
}