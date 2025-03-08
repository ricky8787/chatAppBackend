package com.example.chatapp.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.chatapp.model.User;
import com.example.chatapp.repository.UserRepository;
import com.example.chatapp.request.UserRequest;
import com.example.chatapp.response.UserResponse;

import lombok.RequiredArgsConstructor;
import java.lang.IllegalArgumentException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    /**
     * 
     * @param request
     * @return
     */
    public UserResponse register(UserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword())); 
        user.setNickname(request.getNickname());

        userRepository.save(user);

        return new UserResponse(user.getId(), user.getUsername(), user.getNickname());
    }
    /**
     * 
     * @param request
     * @return
     */
    public List<UserResponse> listAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getNickname()))
                .collect(Collectors.toList());
    }
    /**
     * 
     * @param request
     * @return
     */
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        return new UserResponse(user.getId(), user.getUsername(), user.getNickname());
    }
    /**
     * 
     * @param request
     * @return
     */
    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        return new UserResponse(user.getId(), user.getUsername(), user.getNickname());
    }
    
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

}
