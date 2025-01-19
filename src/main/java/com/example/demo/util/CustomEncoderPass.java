package com.example.demo.util;

import com.password4j.Hash;
import com.password4j.Password;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomEncoderPass implements PasswordEncoder {

    private final String salt = "moneymatters";
    private final String pepper = "timeismoney";
    @Override
    public String encode(CharSequence rawPassword) {
        Hash hash = Password.hash(rawPassword)
                .addSalt(salt)
                .addPepper(pepper)
                .withArgon2();
        return hash.getResult();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return Password.check(rawPassword,encodedPassword)
                .addSalt(salt)
                .addPepper(pepper)
                .withArgon2();

    }
}
