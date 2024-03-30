package com.murat.userservice.payloads;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.murat.userservice.entities.Role;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserResDto(
        Long id,
        String name,
        String email,
        Role role
) {
}
