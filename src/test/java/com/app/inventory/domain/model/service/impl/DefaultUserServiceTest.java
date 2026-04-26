package com.app.inventory.domain.model.service.impl;

import com.app.inventory.config.AlreadyExistsException;
import com.app.inventory.config.NotFoundException;
import com.app.inventory.domain.model.User;
import com.app.inventory.domain.repository.UserRepository;
import com.app.inventory.domain.service.impl.DefaultUserService;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultUserServiceTest {
  @Mock
  private UserRepository userRepository;
  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private DefaultUserService defaultUserService;

  private User userToCreate;
  private User userToUpdate;
  private User savedUser;
  private String encryptedPassword;
  private Long userId;

  @BeforeEach
  void setUp() {
    Faker faker = new Faker();
    String firstName = faker.name().firstName();
    String lastName = faker.name().lastName();
    String email = faker.internet().emailAddress();
    String plainPassword = faker.credentials().password();
    encryptedPassword = "$2a$10$encrypted" + plainPassword;

    userId = faker.number().numberBetween(1L, 100L);

    userToCreate = new User(null, firstName, lastName, email, plainPassword);
    userToUpdate = new User(null, firstName, lastName, email, null);

    savedUser = new User(userId, firstName, lastName, email, encryptedPassword);
  }

  @Test
  void testCreateUserSuccessfully() {
    when(userRepository.existsByEmail(userToCreate.getEmail())).thenReturn(false);
    when(passwordEncoder.encode(userToCreate.getPassword())).thenReturn(encryptedPassword);
    when(userRepository.save(userToCreate)).thenReturn(savedUser);

    User createdUser = defaultUserService.createUser(userToCreate);

    assertEquals(savedUser, createdUser);
  }

  @Test
  void testUpdateUserSuccessfully() {
    when(userRepository.existsById(userId)).thenReturn(true);
    when(userRepository.existsByEmailAndIdNot(userToUpdate.getEmail(), userId)).thenReturn(false);
    when(userRepository.updateFields(userId, userToUpdate.getFirstName(), userToUpdate.getLastName(), userToUpdate.getEmail())).thenReturn(savedUser);

    User updatedUser = defaultUserService.updateUser(userId, userToUpdate);

    assertEquals(savedUser, updatedUser);
  }

  @Test
  void testCreateUserThrowsExceptionWhenEmailExists() {
    when(userRepository.existsByEmail(userToCreate.getEmail())).thenReturn(true);

    assertThrows(AlreadyExistsException.class, () -> defaultUserService.createUser(userToCreate));
    verify(passwordEncoder, never()).encode(any());
  }

  @Test
  void testUpdateUserThrowsExceptionWhenIdDoesNotExists() {
    when(userRepository.existsById(userId)).thenReturn(false);

    assertThrows(NotFoundException.class, () -> defaultUserService.updateUser(userId, userToUpdate));
  }
}
