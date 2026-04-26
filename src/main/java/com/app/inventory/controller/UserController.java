package com.app.inventory.controller;

import com.app.inventory.domain.model.User;
import com.app.inventory.domain.service.UserService;
import com.app.inventory.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.UsersApi;
import org.openapitools.model.CreateUserRequest;
import org.openapitools.model.UpdateUserRequest;
import org.openapitools.model.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling user operations.
 */
@RestController
@RequiredArgsConstructor
public class UserController implements UsersApi {
  private final UserService userService;
  private final UserMapper userMapper;

  @Override
  public ResponseEntity<UserResponse> createUser(CreateUserRequest createUserRequest) {
    User mappedUser = userMapper.createUserRequestToUser(createUserRequest);
    User createdUser = userService.createUser(mappedUser);
    return new ResponseEntity<>(userMapper.userToUserResponse(createdUser), HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<UserResponse> updateUserById(Long userId,
                                                     UpdateUserRequest updateUserRequest) {
    User mappedUser = userMapper.updateUserRequestToUser(updateUserRequest);
    User updatedUser = userService.updateUser(userId, mappedUser);
    return new ResponseEntity<>(userMapper.userToUserResponse(updatedUser), HttpStatus.OK);
  }
}
