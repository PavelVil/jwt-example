package com.github.pavelvil.jwtexample.repository;

import com.github.pavelvil.jwtexample.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Mono<User> findByUsername(String username);

    Mono<Boolean> existsByEmail(String email);

}
