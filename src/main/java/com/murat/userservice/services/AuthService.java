package com.murat.userservice.services;

import com.murat.userservice.entities.User;
import com.murat.userservice.exceptions.UnauthorizedException;
import com.murat.userservice.payloads.LoginReqDto;
import com.murat.userservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String loginUser(LoginReqDto loginReqDto) {
        User user = userRepository.findByEmail(loginReqDto.email());
        if (user == null || !isValidPassword(loginReqDto.password(), user.getPassword()))
            throw new UnauthorizedException("Wrong credentials, try again");

        return "User logged in successfully";
    }

    private boolean isValidPassword(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
