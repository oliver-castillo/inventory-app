package com.app.inventory.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {
  private String firstName;
  private String lastName;
  private String email;
  private String password;

  public void updateEmail(String email) {
    this.email = email;
  }
}
