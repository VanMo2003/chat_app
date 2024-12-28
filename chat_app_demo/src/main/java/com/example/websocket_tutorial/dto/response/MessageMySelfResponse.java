package com.example.websocket_tutorial.dto.response;

import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageMySelfResponse {
    UserResponse usernameFriend;
    List<MessageCheckIsMyResponse> messages;
}
