package com.example.websocket_tutorial.mapper;

import org.mapstruct.Mapper;

import com.example.websocket_tutorial.dto.request.UserCreationRequest;
import com.example.websocket_tutorial.dto.response.UserResponse;
import com.example.websocket_tutorial.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);
    //
    //    @Mapping(target = "roles", ignore = true)
    //    @Mapping(target = "password", ignore = true)
    //    void updateUser(@MappingTarget User user, UserUpdateRequest request);
    //
    //    @Mapping(target = "roles", ignore = true)
    //    void updateRole(@MappingTarget User user, UserUpdateRoleRequest request);

}
