package com.github.pavelvil.jwtexample.security;

import com.github.pavelvil.jwtexample.entity.User;
import com.github.pavelvil.jwtexample.exception.CheckPasswordException;
import com.github.pavelvil.jwtexample.security.jwt.TokenService;
import com.github.pavelvil.jwtexample.service.RefreshTokenService;
import com.github.pavelvil.jwtexample.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityService {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;

    private final RefreshTokenService refreshTokenService;

    public Mono<TokenData> processPasswordToken(String username, String password) {
        return userService.findByUsername(username)
                .flatMap(user -> {
                    if (!passwordEncoder.matches(password, user.getPassword())) {
                        log.error("Exception trying to check password for user: {}", username);
                        return Mono.error(new CheckPasswordException());
                    }

                    return createTokenData(user);
                });
    }

    public Mono<TokenData> processRefreshToken(String refreshTokenValue) {
        return refreshTokenService.getByValue(refreshTokenValue)
                .flatMap(refreshToken -> userService.findById(refreshToken.getUserId()))
                .flatMap(this::createTokenData);
    }

    private Mono<TokenData> createTokenData(User user) {
        String token = tokenService.generateToken(
                user.getUsername(),
                user.getId(),
                user.getRoles().stream().map(Enum::name).collect(Collectors.toList())
        );

        return refreshTokenService.save(user.getId())
                .flatMap(refreshToken -> Mono.just(new TokenData(token, refreshToken.getValue())));
    }


    public record TokenData(String token, String refreshToken) {

    }

}
