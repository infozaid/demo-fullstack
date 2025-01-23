package com.example.demo.user;

import java.util.List;

public record UserDTO(
        Integer id,
        String userName,
        String email,
        List<String> roles
) {
}
