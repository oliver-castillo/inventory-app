package com.app.inventory.persistence.repository;

import com.app.inventory.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
  boolean existsByEmail(String email);
}