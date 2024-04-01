package com.murat.userservice.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginReqDto(
        @NotBlank @Email
        String email,

        @NotBlank
        String password
) {}
