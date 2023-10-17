package com.toyproject.internbrew_backend.user.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class JwtToken {

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }
}
