package com.example.demo.auth.payload;

public record AuthenticationRequest(
        String username,
        String password
) {
}
