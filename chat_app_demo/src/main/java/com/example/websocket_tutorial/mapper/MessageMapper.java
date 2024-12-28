package com.example.websocket_tutorial.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.websocket_tutorial.dto.request.MessageRequest;
import com.example.websocket_tutorial.dto.response.MessageResponse;
import com.example.websocket_tutorial.entity.Message;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    Message toMessage(MessageRequest request);

    @Mapping(target = "sender", ignore = true)
    @Mapping(target = "receiver", ignore = true)
    MessageResponse toMessageResponse(Message message);
}
