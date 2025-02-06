package com.example.demo.user;

import com.example.demo.user.role.Role;

import java.util.Set;

public record UserRegistrationRequest(
        String name,
        String email,
        String password,
        Set<String> roles
) {
}
