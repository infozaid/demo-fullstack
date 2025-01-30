package com.example.demo.user;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/controller")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{userId}")
    public UserDTO getCustomer(@PathVariable("userId") Integer userId){
        return userService.getUser(userId);
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest){
        userService.addUser(userRegistrationRequest);
        return ResponseEntity.ok().body(userRegistrationRequest.email());
    }
}
