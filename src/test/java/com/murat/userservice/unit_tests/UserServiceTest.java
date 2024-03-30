package com.murat.userservice.unit_tests;


import com.murat.userservice.entities.Role;
import com.murat.userservice.entities.User;
import com.murat.userservice.payloads.ResponseDto;
import com.murat.userservice.payloads.UserReqDto;
import com.murat.userservice.repositories.UserRepository;
import com.murat.userservice.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository mockUserRepository;
    @InjectMocks
    private UserService userService;

    @Test
    void test_create_user() {
        // Given
        UserReqDto userReqDto = new UserReqDto("john doe", "john.doe@email.com", "password123", Role.ADMIN);
        User savedUser = User.builder()
                .id(1L)
                .name(userReqDto.name())
                .email(userReqDto.email())
                .password(userReqDto.password())
                .role(userReqDto.role())
                .build();

        when(mockUserRepository.save(any(User.class))).thenReturn(savedUser);

        // When
        ResponseDto response = userService.createUser(userReqDto);

        // Then
        assertEquals(HttpStatus.CREATED.value(), response.status());
        assertEquals("User created successfully with id: 1", response.message());
    }
}
