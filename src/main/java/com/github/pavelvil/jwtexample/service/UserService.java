package com.github.pavelvil.jwtexample.service;

import com.github.pavelvil.jwtexample.entity.User;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<User> create(User user);

    Mono<User> findByUsername(String username);

    Mono<User> findById(String id);

}
