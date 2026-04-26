package com.app.inventory.persistence.repository;

import com.app.inventory.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
  boolean existsByEmail(String email);

  boolean existsByEmailAndIdIsNot(String email, Long id);

  @Modifying
  @Query("UPDATE UserEntity u SET "
      + "u.firstName = :firstName, "
      + "u.lastName = :lastName, "
      + "u.email = :email "
      + "WHERE u.id = :id")
  void updateFields(Long id, String firstName, String lastName, String email);
}
