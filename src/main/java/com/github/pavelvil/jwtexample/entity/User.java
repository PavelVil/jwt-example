package com.github.pavelvil.jwtexample.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "user")
public class User {

    @Id
    @Indexed
    private String id;

    @Indexed
    private String username;

    private String email;

    private String password;

    @Field("roles")
    private Set<RoleType> roles = new HashSet<>();

}
