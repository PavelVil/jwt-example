package com.github.pavelvil.jwtexample.service;

import com.github.pavelvil.jwtexample.entity.RefreshToken;
import reactor.core.publisher.Mono;

public interface RefreshTokenService {

    Mono<RefreshToken> save(String userId);

    Mono<RefreshToken> getByValue(String refreshToken);

}
