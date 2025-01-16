package com.example.demo.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface  UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserWithName(String username);
    Boolean userExistWithName(String username);
    Boolean userExistWithEmail(String email);
}
