package com.github.pavelvil.jwtexample.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UserController {

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public Mono<String> user() {
        return Mono.just("User");
    }

}
