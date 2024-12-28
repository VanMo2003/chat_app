package com.example.websocket_tutorial.dto.response;

import java.util.Date;

import com.example.websocket_tutorial.entity.User;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageResponse {
    User sender;
    User receiver;
    String message;
    Date onCreate;
}
