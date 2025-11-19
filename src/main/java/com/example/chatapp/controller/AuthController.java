package com.example.chatapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.chatapp.request.UserRequest;
import com.example.chatapp.response.UserResponse;
import com.example.chatapp.service.JwtService;
import com.example.chatapp.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequest request) {
    	try {
            UserResponse userResponse = userService.register(request);
            System.out.println("✅ 註冊成功：" + userResponse);
            return ResponseEntity.ok(userResponse); // ✅ 成功回傳 200 + user 資料
        } catch (IllegalArgumentException e) { // ❌ 使用者已存在
//        	System.out.println("❌ 錯誤：使用者已存在");
            return ResponseEntity.status(400).body("Username already exists");
        } catch (Exception e) { // ❌ 其他錯誤
//        	System.out.println("❌ 錯誤：" + e.getMessage());
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        String token = jwtService.generateToken(request.getUsername());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
}

