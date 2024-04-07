package com.murat.userservice.controllers;

import com.murat.userservice.payloads.ResponseDto;
import com.murat.userservice.payloads.UserReqDto;
import com.murat.userservice.payloads.UserResDto;
import com.murat.userservice.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto registerUser(@RequestBody @Valid UserReqDto userReqDto) {
        return userService.createUser(userReqDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResDto> getUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserResDto getUser(@PathVariable long userId) {
        return userService.findUser(userId);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto updateUser(@PathVariable long userId, @RequestBody @Valid UserReqDto userReqDto) {
        return userService.updateUser(userId, userReqDto);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto deleteUser(@PathVariable long userId) {
        return userService.deleteUser(userId);
    }
}
