package com.example.demo.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface  UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserByUsername(String username);
    boolean existsUserById(Integer id);
    boolean existsUserByEmail(String email);

}
