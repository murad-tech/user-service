package com.murat.userservice.unit_tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.murat.userservice.entities.Role;
import com.murat.userservice.payloads.ResponseDto;
import com.murat.userservice.payloads.UserReqDto;
import com.murat.userservice.payloads.UserResDto;
import com.murat.userservice.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService mockUserService;

    private final String BASE_PATH = "/api/users";

    @Test
    void testUserRegistration() throws Exception {
        // Given
        UserReqDto newUser = new UserReqDto("NewUser", "newuser@example.com", "password123", Role.ADMIN);
        ResponseDto response = new ResponseDto(201, "User created successfully with id: 1");
        when(mockUserService.createUser(any(UserReqDto.class))).thenReturn(response);

        // When
        ResultActions result = mockMvc.perform(post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(newUser)));

        // Then
        result.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(response)));
    }

    @Test
    void testGetAllUsersShouldReturnEmptyArray() throws Exception {
        // When
        ResultActions result = mockMvc.perform(get(BASE_PATH));

        // Then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void testGetAllUsersShouldReturnUsersArray() throws Exception {
        // Given
        List<UserResDto> users = Arrays.asList(
                new UserResDto(1L, "User1", "user1@example.com", Role.USER),
                new UserResDto(2L, "User2", "user2@example.com", Role.ADMIN)
        );
        when(mockUserService.findAllUsers()).thenReturn(users);

        // When
        ResultActions result = mockMvc.perform(get(BASE_PATH));

        // Then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("User1")))
                .andExpect(jsonPath("$[0].email", is("user1@example.com")))
                .andExpect(jsonPath("$[0].role", is("USER")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("User2")))
                .andExpect(jsonPath("$[1].email", is("user2@example.com")))
                .andExpect(jsonPath("$[1].role", is("ADMIN")));
    }

    @Test
    void testGetUserById() throws Exception {
        // Given
        long userId = 1L;
        UserResDto user = new UserResDto(userId, "John Doe", "john.doe@email.com", Role.USER);
        when(mockUserService.findUser(userId)).thenReturn(user);

        // When
        ResultActions result = mockMvc.perform(get(BASE_PATH+"/{id}", userId));

        // Then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@email.com"))
                .andExpect(jsonPath("$.role").value("USER"));
    }

    @Test
    void updateUser() throws Exception {
        // Given
        long userId = 1L;
        UserReqDto user = new UserReqDto("John Doe", "john.doe@email.com", "password123", Role.USER);
        ResponseDto response = new ResponseDto(200, "User updated successfully");
        when(mockUserService.updateUser(eq(userId), any(UserReqDto.class))).thenReturn(response);

        // When
        ResultActions result = mockMvc.perform(put(BASE_PATH+"/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user)));

        // Then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("User updated successfully"));
    }

    @Test
    void deleteUser() throws Exception {
        // Given
        long userId = 1L;
        ResponseDto response = new ResponseDto(200, "User deleted successfully");
        when(mockUserService.deleteUser(userId)).thenReturn(response);

        // When
        ResultActions result = mockMvc.perform(delete(BASE_PATH+"/{id}", userId));

        // Then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("User deleted successfully"));
    }
}
