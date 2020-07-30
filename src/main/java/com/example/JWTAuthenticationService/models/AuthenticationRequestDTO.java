package com.example.JWTAuthenticationService.models;

import lombok.Data;

@Data
public class AuthenticationRequestDTO {

    private String username;
    private String password;
}
