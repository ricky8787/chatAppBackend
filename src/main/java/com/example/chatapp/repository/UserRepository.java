package com.example.chatapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.chatapp.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	
	@Query("SELECT u FROM User u WHERE u.username IN (:usernames)")
	List<User> findByUsernames(@Param("usernames") List<String> usernames);
	
	boolean existsByUsername(String username);

}
