package com.app.inventory.controller;

import org.openapitools.api.UsersApi;
import org.openapitools.model.User;
import org.openapitools.model.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UsersApi {
  @Override
  public ResponseEntity<User> createUser(UserRequest userRequest) {
    return UsersApi.super.createUser(userRequest);
  }
}
