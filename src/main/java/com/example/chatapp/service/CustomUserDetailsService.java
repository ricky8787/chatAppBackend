package com.example.chatapp.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.chatapp.entity.User;
import com.example.chatapp.repository.UserRepository;

import lombok.RequiredArgsConstructor;
/**
 * 專門負責 Spring Security 認證
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User users = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(users.getUsername())
                .password(users.getPassword()) 
                .authorities("USER") // 這邊可以改成你的權限管理
                .build();
    }
}
