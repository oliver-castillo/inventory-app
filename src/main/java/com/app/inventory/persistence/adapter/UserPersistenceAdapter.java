package com.app.inventory.persistence.adapter;

import com.app.inventory.config.ErrorMessage;
import com.app.inventory.config.NotFoundException;
import com.app.inventory.domain.model.User;
import com.app.inventory.domain.repository.UserRepository;
import com.app.inventory.mapper.UserMapper;
import com.app.inventory.persistence.entity.UserEntity;
import com.app.inventory.persistence.repository.JpaUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserRepository {

  private final JpaUserRepository jpaUserRepository;
  private final UserMapper userMapper;

  @Override
  @Transactional
  public User save(User user) {
    UserEntity entity = userMapper.userToUserEntity(user);
    UserEntity saved = jpaUserRepository.save(entity);
    return userMapper.userEntityToUser(saved);
  }

  @Override
  @Transactional
  public User updateFields(Long id, String firstName, String lastName, String email) {
    jpaUserRepository.updateFields(id, firstName, lastName, email);
    return findById(id);
  }

  @Override
  public User findById(Long id) {
    return jpaUserRepository.findById(id)
        .map(userMapper::userEntityToUser)
        .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND));
  }

  @Override
  public boolean existsByEmail(String email) {
    return jpaUserRepository.existsByEmail(email);
  }

  @Override
  public boolean existsById(Long id) {
    return jpaUserRepository.existsById(id);
  }

  @Override
  public boolean existsByEmailAndIdNot(String email, Long id) {
    return jpaUserRepository.existsByEmailAndIdIsNot(email, id);
  }
}