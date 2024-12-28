package com.example.websocket_tutorial.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.websocket_tutorial.dto.request.MessageRequest;
import com.example.websocket_tutorial.dto.response.MessageCheckIsMyResponse;
import com.example.websocket_tutorial.dto.response.MessageMySelfResponse;
import com.example.websocket_tutorial.dto.response.MessagePublicResponse;
import com.example.websocket_tutorial.dto.response.MessageResponse;
import com.example.websocket_tutorial.entity.Message;
import com.example.websocket_tutorial.entity.User;
import com.example.websocket_tutorial.exception.AppException;
import com.example.websocket_tutorial.exception.ErrorCode;
import com.example.websocket_tutorial.mapper.MessageMapper;
import com.example.websocket_tutorial.mapper.UserMapper;
import com.example.websocket_tutorial.repository.MessageRepository;
import com.example.websocket_tutorial.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageService {
    MessageRepository messageRepository;
    MessageMapper messageMapper;
    UserRepository userRepository;
    UserMapper userMapper;

    public MessageResponse savePrivateMessage(MessageRequest request, String usernameMySelf) {
        User sender = userRepository.findByUsername(usernameMySelf).orElseThrow(() -> {
            throw new AppException(ErrorCode.SENDER_NOT_EXISTED);
        });
        User receiver = null;
        if (request.getReceiver() != null) {
            receiver = userRepository.findByUsername(request.getReceiver()).orElseThrow(() -> {
                throw new AppException(ErrorCode.RECEIVER_NOT_EXISTED);
            });
        }

        Message message = messageMapper.toMessage(request);
        message.setSender(usernameMySelf);

        MessageResponse messageResponse = messageMapper.toMessageResponse(messageRepository.save(message));
        messageResponse.setSender(sender);
        messageResponse.setReceiver(receiver);

        return messageResponse;
    }

    public List<MessagePublicResponse> getMessagePublicByUser() {
        var context = SecurityContextHolder.getContext();
        String usernameMySelf = context.getAuthentication().getName();

        List<MessagePublicResponse> messagePublicResponses = new ArrayList<>();

        messageRepository.findAll().forEach(message -> {
            if (message.getReceiver() == null) {
                MessagePublicResponse messagePublicResponse = new MessagePublicResponse();

                User sender = userRepository
                        .findByUsername(message.getSender())
                        .orElseThrow(() -> new AppException(ErrorCode.SENDER_NOT_EXISTED));
                messagePublicResponse.setSender(userMapper.toUserResponse(sender));
                messagePublicResponse.setContent(message.getMessage());
                messagePublicResponse.setMy(message.getSender().equals(usernameMySelf));
                messagePublicResponse.setOnCreate(message.getOnCreate());

                messagePublicResponses.add(messagePublicResponse);
            }
        });

        return messagePublicResponses;
    }

    public List<MessageMySelfResponse> getMessageMySelf() {
        var context = SecurityContextHolder.getContext();
        String usernameReceiver = context.getAuthentication().getName();

        List<MessageMySelfResponse> messageMySelfResponses = new ArrayList<>();

        messageRepository.findAllMySelf(usernameReceiver).forEach(message -> {
            if (message.getReceiver() != null) {
                String usernameFriend =
                        message.getSender().equals(usernameReceiver) ? message.getReceiver() : message.getSender();
                String contentMessage = message.getMessage();
                boolean isMy = message.getSender().equals(usernameReceiver);
                Date onCreate = message.getOnCreate();
                if (!isFriendExists(messageMySelfResponses, usernameFriend) || messageMySelfResponses.isEmpty()) {
                    User userFriend = userRepository
                            .findByUsername(usernameFriend)
                            .orElseThrow(() -> new AppException(ErrorCode.RECEIVER_NOT_EXISTED));

                    List<MessageCheckIsMyResponse> messages = new ArrayList<>();
                    messages.add(new MessageCheckIsMyResponse(contentMessage, isMy, onCreate));

                    messageMySelfResponses.add(
                            new MessageMySelfResponse(userMapper.toUserResponse(userFriend), messages));
                } else {
                    for (MessageMySelfResponse response : messageMySelfResponses) {
                        if (response.getUsernameFriend().getUsername().equals(usernameFriend)) {
                            response.getMessages().add(new MessageCheckIsMyResponse(contentMessage, isMy, onCreate));
                        }
                    }
                }
            }
        });

        return messageMySelfResponses;
    }

    boolean isFriendExists(List<MessageMySelfResponse> messageMySelfResponses, String friend) {
        for (MessageMySelfResponse response : messageMySelfResponses) {
            if (response.getUsernameFriend().getUsername().equals(friend)) {
                return true;
            }
        }

        return false;
    }
}
