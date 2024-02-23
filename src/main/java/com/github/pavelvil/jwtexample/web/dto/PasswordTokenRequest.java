package com.github.pavelvil.jwtexample.web.dto;

import lombok.Data;

@Data
public class PasswordTokenRequest {

    private String username;

    private String password;

}
