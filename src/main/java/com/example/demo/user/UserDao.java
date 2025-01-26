package com.example.demo.user;

import java.util.Optional;

public interface UserDao {

    Optional <User> selectUserById(Integer id);
    boolean existsUserById(Integer id);
    boolean existsUserWithEmail(String email);
    void insertUser(User user);
}
