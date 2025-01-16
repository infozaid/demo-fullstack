package com.example.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements  UserDetailsService{

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserWithName(username)
                .orElseThrow(()->new UsernameNotFoundException("User Not Found with username: "+username));
        return UserDetailsImpl.build(user);
    }
}
