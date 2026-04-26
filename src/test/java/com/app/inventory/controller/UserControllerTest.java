package com.app.inventory.controller;

import com.app.inventory.domain.model.User;
import com.app.inventory.domain.service.UserService;
import com.app.inventory.mapper.UserMapper;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.CreateUserRequest;
import org.openapitools.model.UpdateUserRequest;
import org.openapitools.model.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

  @Mock
  private UserService userService;

  @Mock
  private UserMapper userMapper;

  @InjectMocks
  private UserController userController;

  private CreateUserRequest createUserRequest;
  private UpdateUserRequest updateUserRequest;
  private User user;
  private UserResponse userResponse;
  private Long userId;

  @BeforeEach
  void setUp() {
    Faker faker = new Faker();
    userId = faker.number().numberBetween(1L, 100L);
    String firstName = faker.name().firstName();
    String lastName = faker.name().lastName();
    String email = faker.internet().emailAddress();

    createUserRequest = new CreateUserRequest();
    createUserRequest.setFirstName(firstName);
    createUserRequest.setLastName(lastName);
    createUserRequest.setEmail(email);

    updateUserRequest = new UpdateUserRequest();
    updateUserRequest.setFirstName(firstName);
    updateUserRequest.setLastName(lastName);
    updateUserRequest.setEmail(email);

    user = new User();
    user.setId(userId);
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setEmail(email);

    userResponse = new UserResponse();
    userResponse.setId(userId);
    userResponse.setFirstName(firstName);
    userResponse.setLastName(lastName);
    userResponse.setEmail(email);
  }

  @Test
  void createUser_ShouldReturn201_WhenUserIsCreated() {
    // Arrange
    when(userMapper.createUserRequestToUser(createUserRequest)).thenReturn(user);
    when(userService.createUser(user)).thenReturn(user);
    when(userMapper.userToUserResponse(user)).thenReturn(userResponse);

    // Act
    ResponseEntity<UserResponse> response = userController.createUser(createUserRequest);

    // Assert
    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(userResponse, response.getBody());

    verify(userService).createUser(user);
  }

  @Test
  void testUpdateUserByIdSuccessfully() {
    when(userMapper.updateUserRequestToUser(updateUserRequest)).thenReturn(user);
    when(userService.updateUser(userId, user)).thenReturn(user);
    when(userMapper.userToUserResponse(user)).thenReturn(userResponse);

    ResponseEntity<UserResponse> response = userController.updateUserById(userId, updateUserRequest);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(userResponse, response.getBody());

    verify(userService).updateUser(userId, user);
    verify(userMapper).updateUserRequestToUser(updateUserRequest);
    verify(userMapper).userToUserResponse(user);
  }
}