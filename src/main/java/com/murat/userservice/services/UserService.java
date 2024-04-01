package com.murat.userservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.murat.userservice.payloads.LoginReqDto;
import com.murat.userservice.payloads.ResponseDto;
import com.murat.userservice.payloads.UserReqDto;
import com.murat.userservice.payloads.UserResDto;
import com.murat.userservice.entities.User;
import com.murat.userservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    ObjectMapper mapper = new ObjectMapper();
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public ResponseDto createUser(UserReqDto userReqDto) {
        User user = User.builder()
                .name(userReqDto.name())
                .email(userReqDto.email())
                .password(encoder.encode(userReqDto.password()))
                .role(userReqDto.role())
                .build();

        long userId = userRepository.save(user).getId();
        return new ResponseDto(HttpStatus.CREATED.value(), "User created successfully with id: " + userId);
    }

    public List<UserResDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> mapper.convertValue(user, UserResDto.class)).toList();
    }

    public UserResDto findUser(long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(value -> mapper.convertValue(value, UserResDto.class)).orElse(null);
    }

    public ResponseDto updateUser(Long id, UserReqDto userReqDto) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) return new ResponseDto(HttpStatus.NOT_FOUND.value(), "User not found");

        if (userReqDto.name() != null) user.get().setName(userReqDto.name());
        if (userReqDto.email() != null) user.get().setEmail(userReqDto.email());
        if (userReqDto.password() != null) user.get().setPassword(encoder.encode(userReqDto.password()));
        if (userReqDto.role() != null) user.get().setRole(userReqDto.role());
        userRepository.save(user.get());

        return new ResponseDto(HttpStatus.OK.value(), "User updated successfully");
    }

    public ResponseDto deleteUser(long userId) {
        userRepository.deleteById(userId);
        return new ResponseDto(HttpStatus.OK.value(), "User successfully deleted");
    }
}
