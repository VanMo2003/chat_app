package com.example.websocket_tutorial.entity;

import java.util.Date;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String sender;
    String receiver;
    String message;

    Date onCreate;

    @Override
    public String toString() {
        return "Message{" + "sender='"
                + sender + '\'' + ", receiver='"
                + receiver + '\'' + ", message='"
                + message + '\'' + ", onCreate="
                + onCreate + '}';
    }
}
