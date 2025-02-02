package com.example.demo.auth;


import com.example.demo.auth.payload.AuthenticationRequest;
import com.example.demo.auth.payload.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest){
        AuthenticationResponse response  = authenticationService.login(authenticationRequest);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION,response.token())
                .body(response);
    }
}
