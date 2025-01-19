package com.example.demo.Security;

import com.example.demo.util.CustomEncoderPass;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    private final CustomEncoderPass customEncoderPass;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName().trim();
        UserDetails userDetails =  userDetailsService.loadUserByUsername(username);
        boolean isPasswordValid = customEncoderPass.matches(authentication.getCredentials().toString().trim(),userDetails.getPassword());

        if(!isPasswordValid){
            throw new BadCredentialsException("Invalid Login Credentials");
        }

        authentication = new UsernamePasswordAuthenticationToken(userDetails,userDetails.getPassword(), userDetails.getAuthorities());

        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
