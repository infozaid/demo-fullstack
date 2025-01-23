package com.example.demo.auth.payload;

public record AuthenticationRequest(
        String userName,
        String Password
) {
}
