package com.example.demo.user;

import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository("jpa")
public class UserJPADataAccessService implements UserDao{

    private final UserRepository userRepository;

    public UserJPADataAccessService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> selectUserById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean existsUserById(Integer id) {
        return userRepository.existsUserById(id);
    }

    @Override
    public boolean existsUserWithEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    @Override
    public void insertUser(User user) {
        userRepository.save(user);
    }
}
