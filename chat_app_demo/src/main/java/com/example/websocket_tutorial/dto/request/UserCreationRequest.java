package com.example.websocket_tutorial.dto.request;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 3, message = "INVALID_USERNAME")
    String username;

    @Size(min = 6, message = "INVALID_PASSWORD")
    String password;

    String fullName;
    String phone;
    String gender;

    LocalDate dateOfBirth;

    String birthPlace;
    String address;
    List<String> roles = List.of("USER");
    boolean active = true;
    Date onCreate = new Date();
    Date onUpdate = new Date();
}
