package com.app.inventory.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
  USER_NOT_FOUND("User not found"),
  EMAIL_ALREADY_EXISTS("Email already exists");

  private final String message;
}
