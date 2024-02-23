package com.github.pavelvil.jwtexample.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public Mono<String> admin() {
        return Mono.just("Admin");
    }

}
