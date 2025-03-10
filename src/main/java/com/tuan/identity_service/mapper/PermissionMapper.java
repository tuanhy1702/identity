package com.tuan.identity_service.mapper;

import org.mapstruct.Mapper;

import com.tuan.identity_service.dto.request.PermissionRequest;
import com.tuan.identity_service.dto.response.PermissionResponse;
import com.tuan.identity_service.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    // @Mapping(target = "lastName", ignore = true) bỏ qua lastName
    PermissionResponse toPermissionResponse(Permission permission);
}
