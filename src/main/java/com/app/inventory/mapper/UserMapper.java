package com.app.inventory.mapper;

import com.app.inventory.domain.model.User;
import com.app.inventory.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.openapitools.model.CreateUserRequest;
import org.openapitools.model.UpdateUserRequest;
import org.openapitools.model.UserResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
  UserEntity userToUserEntity(User user);

  UserResponse userToUserResponse(User user);

  User createUserRequestToUser(CreateUserRequest createUserRequest);

  User updateUserRequestToUser(UpdateUserRequest updateUserRequest);

  User userEntityToUser(UserEntity userEntity);
}
