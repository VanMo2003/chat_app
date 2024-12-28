package com.example.websocket_tutorial.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.example.websocket_tutorial.dto.request.UserCreationRequest;
import com.example.websocket_tutorial.dto.response.ApiResponse;
import com.example.websocket_tutorial.dto.response.UserResponse;
import com.example.websocket_tutorial.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @GetMapping
    ApiResponse<List<UserResponse>> getAllUsers() {
        ApiResponse<List<UserResponse>> response = new ApiResponse<>();
        response.setData(userService.getAllUsers());
        return response;
    }

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setData(userService.createUser(request));
        return response;
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUserById(@PathVariable String userId) {
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setData(userService.getUserById(userId));
        return response;
    }

    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder().data(userService.getMyInfo()).build();
    }

    @GetMapping("/checkExistUser/{username}")
    ApiResponse<Boolean> checkExistUser(@PathVariable String username) {
        return ApiResponse.<Boolean>builder()
                .data(userService.checkExistUser(username))
                .build();
    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUserById(@PathVariable String userId) {
        userService.deleteUserById(userId);
        ApiResponse response = new ApiResponse<>();
        response.setData("User has been deleted");
        return response;
    }
}
