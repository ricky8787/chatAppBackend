package com.example.chatapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.chatapp.common.ApiResponseFactory;
import com.example.chatapp.request.UserRequest;
import com.example.chatapp.response.ApiResponse;
import com.example.chatapp.response.UserResponse;
import com.example.chatapp.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<UserResponse>> getUserByUsername(@RequestParam String username) {
        UserResponse response = userService.getUserByUsername(username);
        return ApiResponseFactory.success(response, "查詢成功");
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> listAllUsers() {
        List<UserResponse> users = userService.listAllUsers();
        return ApiResponseFactory.success(users, "查詢成功");
    }
    
}
