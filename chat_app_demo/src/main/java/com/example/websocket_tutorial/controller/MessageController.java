package com.example.websocket_tutorial.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.websocket_tutorial.dto.request.MessageRequest;
import com.example.websocket_tutorial.dto.response.ApiResponse;
import com.example.websocket_tutorial.dto.response.MessageMySelfResponse;
import com.example.websocket_tutorial.dto.response.MessagePublicResponse;
import com.example.websocket_tutorial.dto.response.MessageResponse;
import com.example.websocket_tutorial.service.MessageService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MessageController {
    MessageService messageService;

    @PostMapping("/sendPrivateMessage")
    public ApiResponse<MessageResponse> sendPrivateMessage(@RequestBody MessageRequest request, Principal principal) {
        System.out.println(request);
        return ApiResponse.<MessageResponse>builder()
                .data(messageService.savePrivateMessage(request, principal.getName()))
                .build();
    }

    @GetMapping("/mySelf")
    public ApiResponse<List<MessageMySelfResponse>> getMessageMySelf() {
        return ApiResponse.<List<MessageMySelfResponse>>builder()
                .data(messageService.getMessageMySelf())
                .build();
    }

    @GetMapping("/public-mySelf")
    public ApiResponse<List<MessagePublicResponse>> getMessagePublicByUser() {
        return ApiResponse.<List<MessagePublicResponse>>builder()
                .data(messageService.getMessagePublicByUser())
                .build();
    }
}
