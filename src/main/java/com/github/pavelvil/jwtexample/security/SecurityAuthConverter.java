package com.github.pavelvil.jwtexample.security;

import com.github.pavelvil.jwtexample.security.jwt.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SecurityAuthConverter implements ServerAuthenticationConverter {

    private static final String BEARER_PREFIX = "Bearer ";

    private final TokenService tokenService;

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange)
                .flatMap(this::extractBearerToken)
                .flatMap(token -> {
                    if (tokenService.validate(token)) {
                        return tokenService.toAuthentication(token);
                    }

                    return Mono.empty();
                });
    }

    private Mono<String> extractBearerToken(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest()
                        .getHeaders()
                        .getFirst(HttpHeaders.AUTHORIZATION))
                .flatMap(token -> {
                    if (token.startsWith(BEARER_PREFIX)) {
                        return Mono.just(token.substring(BEARER_PREFIX.length()));
                    }

                    return Mono.empty();
                });
    }
}
