package com.example.websocket_tutorial.controller.ws;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.websocket_tutorial.dto.request.MessageRequest;
import com.example.websocket_tutorial.dto.response.ReceiverResponse;
import com.example.websocket_tutorial.service.MessageService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MessageWS {
    MessageService messageService;
    SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/public-message")
    @SendTo("/chatroom/public")
    public MessageRequest receiverPublicMessage(@Payload MessageRequest request, Principal principal) {
        messageService.savePrivateMessage(request, principal.getName());
        return request;
    }

    @MessageMapping("/private-message")
    public MessageRequest receiverPrivateMessage(@Payload MessageRequest request, Principal principal) {
        messageService.savePrivateMessage(request, principal.getName());
        ReceiverResponse response = new ReceiverResponse();
        response.setSender(principal.getName());
        response.setReceiver(request.getReceiver());
        response.setMessage(request.getMessage());
        response.setOnCreate(request.getOnCreate());
        simpMessagingTemplate.convertAndSendToUser(request.getReceiver(), "/private", response);

        return request;
    }
}
