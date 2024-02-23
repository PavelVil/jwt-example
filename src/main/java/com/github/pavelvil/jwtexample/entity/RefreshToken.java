package com.github.pavelvil.jwtexample.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken implements Serializable {

    private String id;

    private String userId;

    private String value;

}
