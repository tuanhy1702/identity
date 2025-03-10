package com.tuan.identity_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.tuan.identity_service.dto.request.UserCreationRequest;
import com.tuan.identity_service.dto.request.UserUpdateRequest;
import com.tuan.identity_service.dto.response.UserResponse;
import com.tuan.identity_service.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    // @Mapping(target = "lastName", ignore = true) bỏ qua lastName
    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
