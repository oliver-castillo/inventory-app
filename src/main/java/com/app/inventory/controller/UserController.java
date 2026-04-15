package com.app.inventory.controller;

import com.app.inventory.domain.model.User;
import com.app.inventory.domain.service.UserService;
import com.app.inventory.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.UsersApi;
import org.openapitools.model.UserRequest;
import org.openapitools.model.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UsersApi {
  private final UserService userService;
  private final UserMapper userMapper;

  @Override
  public ResponseEntity<UserResponse> createUser(UserRequest userRequest) {
    User createdUser = userService.createUser(userMapper.userRequestToUser(userRequest));
    return new ResponseEntity<>(userMapper.userToUserResponse(createdUser), HttpStatus.CREATED);
  }
}
