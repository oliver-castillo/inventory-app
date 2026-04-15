package com.app.inventory.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyExistsException extends RuntimeException {
  public AlreadyExistsException(ErrorMessage errorMessage) {
    super(errorMessage.getMessage());
  }
}
