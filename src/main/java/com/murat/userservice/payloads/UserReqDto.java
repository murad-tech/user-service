package com.murat.userservice.payloads;

import com.murat.userservice.entities.Role;

public record UserReqDto(
    String name,
    String email,
    String password,
    Role role
) {
    public UserReqDto {
        role = role == null ? Role.USER : role;
    }
}
