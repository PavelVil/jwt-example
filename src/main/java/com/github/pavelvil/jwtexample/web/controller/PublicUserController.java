package com.github.pavelvil.jwtexample.web.controller;

import com.github.pavelvil.jwtexample.entity.RoleType;
import com.github.pavelvil.jwtexample.entity.User;
import com.github.pavelvil.jwtexample.service.UserService;
import com.github.pavelvil.jwtexample.web.dto.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/public/user")
@RequiredArgsConstructor
public class PublicUserController {

    private final UserService userService;

    @PostMapping
    public Mono<ResponseEntity<String>> createUser(@RequestBody CreateUserRequest request) {
        return userService.create(User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .username(request.getUsername())
                .roles(request.getRoles().stream()
                        .map(it -> RoleType.valueOf(it.toUpperCase())).collect(Collectors.toSet()))
                .build())
                .map(user -> ResponseEntity.ok("User successfully created!"));
    }

}
