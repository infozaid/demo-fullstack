package com.example.demo.user;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


@Transactional
public interface  UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findUserByUsername(String username);
    boolean existsUserById(Integer id);
    boolean existsUserByEmail(String email);

}
