package com.example.chatapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.chatapp.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    // ✅ Spring Data 會自動產生：SELECT u FROM Users u WHERE u.username IN :usernames
    List<User> findByUsernameIn(List<String> usernames);

    boolean existsByUsername(String username);

}
