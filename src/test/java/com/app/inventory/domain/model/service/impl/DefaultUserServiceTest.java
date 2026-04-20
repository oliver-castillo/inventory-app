package com.app.inventory.domain.model.service.impl;

import com.app.inventory.config.AlreadyExistsException;
import com.app.inventory.domain.model.User;
import com.app.inventory.domain.service.impl.DefaultUserService;
import com.app.inventory.mapper.UserMapper;
import com.app.inventory.persistence.entity.UserEntity;
import com.app.inventory.persistence.repository.JpaUserRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
  private JpaUserRepository jpaUserRepository;
  @Mock
  private UserMapper userMapper;
  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private DefaultUserService defaultUserService;

  private Faker faker;
  private User user;
  private UserEntity userEntity;
  private String plainPassword;
  private String encryptedPassword;

  @BeforeEach
  void setUp() {
    faker = new Faker();
    String firstName = faker.name().firstName();
    String lastName = faker.name().lastName();
    String email = faker.internet().emailAddress();
    plainPassword = faker.credentials().password();
    encryptedPassword = "$2a$10$encrypted" + plainPassword;

    user = new User(firstName, lastName, email, plainPassword);
    userEntity = new UserEntity(firstName, lastName, email, encryptedPassword);
  }

  @Test
  void testCreateUserSuccessfully() {
    when(jpaUserRepository.existsByEmail(user.getEmail())).thenReturn(false);
    when(passwordEncoder.encode(plainPassword)).thenReturn(encryptedPassword);
    when(userMapper.userToUserEntity(any(User.class))).thenReturn(userEntity);
    when(jpaUserRepository.save(userEntity)).thenReturn(userEntity);
    when(userMapper.userEntityToUser(userEntity)).thenReturn(user);

    User createdUser = defaultUserService.createUser(user);

    assertEquals(user, createdUser);
    verify(jpaUserRepository).existsByEmail(user.getEmail());
  }

  @Test
  void testCreateUserThrowsExceptionWhenEmailExists() {
    when(jpaUserRepository.existsByEmail(user.getEmail())).thenReturn(true);

    assertThrows(AlreadyExistsException.class, () -> defaultUserService.createUser(user));
    verify(passwordEncoder, never()).encode(any());
  }

  @Test
  void testCreateUserEncryptsPassword() {
    when(jpaUserRepository.existsByEmail(user.getEmail())).thenReturn(false);
    when(passwordEncoder.encode(plainPassword)).thenReturn(encryptedPassword);
    when(userMapper.userToUserEntity(any(User.class))).thenReturn(userEntity);
    when(jpaUserRepository.save(userEntity)).thenReturn(userEntity);
    when(userMapper.userEntityToUser(userEntity)).thenReturn(user);

    defaultUserService.createUser(user);

    ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
    verify(userMapper).userToUserEntity(captor.capture());
    assertEquals(encryptedPassword, captor.getValue().getPassword());
  }
}
