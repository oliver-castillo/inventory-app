package com.app.inventory.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "\"USERS\"")
@Entity
@Getter
@NoArgsConstructor
public class UserEntity extends AuditableEntity {
  @Column(name = "\"FIRST_NAME\"")
  private String firstName;

  @Column(name = "\"LAST_NAME\"")
  private String lastName;

  @Column(name = "\"EMAIL\"", unique = true)
  private String email;

  @Column(name = "\"PASSWORD\"")
  private String password;

  @Builder
  public UserEntity(String firstName, String lastName, String email, String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
  }
}