package com.tuan.identity_service.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Size;

import com.tuan.identity_service.validator.DobConstraint;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 4, message = "USERNAME_INVALID")
    String username;

    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;

    @DobConstraint(min = 16, message = "INVALID_DOB")
    LocalDate dob;

    String firstName;
    String lastName;
}
