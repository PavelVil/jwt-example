package com.github.pavelvil.jwtexample.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponse {

    private String token;

    private String refreshToken;

}
