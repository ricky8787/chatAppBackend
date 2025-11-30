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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Auth", description = "登入與註冊相關 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Operation(
            summary = "使用者註冊",
            description = "建立新帳號並回傳簡易使用者資訊"
    )
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequest request) {
        try {
            UserResponse userResponse = userService.register(request);
            System.out.println("✅ 註冊成功：" + userResponse);
            return ResponseEntity.ok(userResponse); // ✅ 成功回傳 200 + user 資料
        } catch (IllegalArgumentException e) {
            // 使用者已存在");
            return ResponseEntity.status(400).body("Username already exists");
        } catch (Exception e) {
            // 其他錯誤;
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @Operation(
            summary = "使用者登入",
            description = "驗證帳號密碼並簽發 JWT Token"
    )
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
