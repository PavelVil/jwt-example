package com.github.pavelvil.jwtexample.repository;

import com.github.pavelvil.jwtexample.entity.RefreshToken;
import reactor.core.publisher.Mono;

import java.time.Duration;

public interface RefreshTokenRepository {

    Mono<Boolean> save(RefreshToken refreshToken, Duration expTime);

    Mono<RefreshToken> getByValue(String refreshToken);

}
