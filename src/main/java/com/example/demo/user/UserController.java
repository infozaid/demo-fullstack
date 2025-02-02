package com.example.demo.user;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole;

@RestController
@RequestMapping(path="api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{userId}")
    public UserDTO getCustomer(@PathVariable("userId") Integer userId){
        return userService.getUser(userId);
    }

 //   @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest){
        userService.addUser(userRegistrationRequest);
        return ResponseEntity.ok().body(userRegistrationRequest.email());
    }
}
