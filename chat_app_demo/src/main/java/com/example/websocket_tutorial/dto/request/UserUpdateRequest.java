package com.example.websocket_tutorial.dto.request;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String password;
    String fullName;
    String phone;
    String gender;
    LocalDate dateOfBirth;

    String birthPlace;
    String address;
    List<String> roles;
    boolean active = true;
    Date onUpdate = new Date();
}
