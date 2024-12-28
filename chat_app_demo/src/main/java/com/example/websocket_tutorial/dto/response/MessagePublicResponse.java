package com.example.websocket_tutorial.dto.response;

import java.util.Date;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessagePublicResponse {
    UserResponse sender;
    String content;
    boolean isMy;
    Date onCreate;
}
