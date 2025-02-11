package com.example.demo.auth;


import com.example.demo.user.UserAuthenticationProvider;
import com.example.demo.Security.jwt.JwtUtils;
import com.example.demo.auth.payload.AuthenticationRequest;
import com.example.demo.auth.payload.AuthenticationResponse;
import com.example.demo.user.User;
import com.example.demo.user.UserDTO;
import com.example.demo.user.UserDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final UserAuthenticationProvider userAuthenticationProvider;

    private final UserDtoMapper userDtoMapper;

    private final JwtUtils jwtUtils;

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest){
        Authentication authentication = userAuthenticationProvider
                .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.username(),
                        authenticationRequest.password()));

        User user = (User) authentication.getPrincipal();


        UserDTO userDTO = userDtoMapper.apply(user);
        String token = jwtUtils.issueToken(userDTO.userName(),userDTO.roles());
        return new AuthenticationResponse(token,userDTO);
    }



}
