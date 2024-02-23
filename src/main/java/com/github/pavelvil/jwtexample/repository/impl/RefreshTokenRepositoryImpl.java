package com.github.pavelvil.jwtexample.repository.impl;

import com.github.pavelvil.jwtexample.entity.RefreshToken;
import com.github.pavelvil.jwtexample.exception.RefreshTokenException;
import com.github.pavelvil.jwtexample.repository.RefreshTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Repository
@Slf4j
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private static final String REFRESH_TOKEN_INDEX = "refreshTokenIndex";

    private final ReactiveValueOperations<String, RefreshToken> operationsForValue;

    private final ReactiveHashOperations<String, String, String> operationsForHash;

    public RefreshTokenRepositoryImpl(ReactiveRedisTemplate<String, RefreshToken> refreshTokenRedisTemplate) {
        this.operationsForValue = refreshTokenRedisTemplate.opsForValue();
        this.operationsForHash = refreshTokenRedisTemplate.opsForHash();
    }

    @Override
    public Mono<Boolean> save(RefreshToken refreshToken, Duration expTime) {
        return operationsForValue.set(refreshToken.getId(), refreshToken, expTime)
                .then(operationsForHash.put(REFRESH_TOKEN_INDEX, refreshToken.getValue(), refreshToken.getId()));
    }

    @Override
    public Mono<RefreshToken> getByValue(String refreshToken) {
        return operationsForHash.get(REFRESH_TOKEN_INDEX, refreshToken)
                .flatMap(refreshTokenId -> operationsForHash.remove(REFRESH_TOKEN_INDEX, refreshToken)
                        .flatMap(cleanupCount -> {
                            log.info("Cleanup refreshToken hash count: " + cleanupCount);

                            return operationsForValue.get(refreshTokenId);
                        }))
                .switchIfEmpty(Mono.error(new RefreshTokenException("Refresh token not found: " + refreshToken)));
    }
}
