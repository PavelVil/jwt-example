package com.github.pavelvil.jwtexample.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping
    public Mono<String> manager() {
        return Mono.just("Manager");
    }

}
