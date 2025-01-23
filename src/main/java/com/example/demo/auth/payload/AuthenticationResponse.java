package com.example.demo.auth.payload;

import com.example.demo.user.UserDTO;

public record AuthenticationResponse(
        String token,
        UserDTO userDTO
) {
}
