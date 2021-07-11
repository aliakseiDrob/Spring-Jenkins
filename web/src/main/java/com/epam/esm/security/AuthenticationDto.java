package com.epam.esm.security;

import lombok.Data;

@Data
public class AuthenticationDto {
    
    private final String name;
    private final String password;
}
