package com.murat.userservice.payloads;

import com.murat.userservice.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserReqDto(
    @NotBlank
    String name,
    @NotBlank @Email
    String email,
    @NotBlank
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*()]).{8,}$",
            message = "must be 8 characters long and combination of uppercase letters, " +
                    "lowercase letters, numbers, special characters.")
    String password,
    Role role
) {
    public UserReqDto {
        role = role == null ? Role.USER : role;
    }
}
