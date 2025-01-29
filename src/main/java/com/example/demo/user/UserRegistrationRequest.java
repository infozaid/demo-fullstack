package com.example.demo.user;

public record UserRegistrationRequest(
        String name,
        String email,
        String password,
        ERole role
) {
}
