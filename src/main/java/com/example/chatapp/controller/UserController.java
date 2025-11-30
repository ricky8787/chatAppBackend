package com.example.chatapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.chatapp.common.ApiResponseFactory;
import com.example.chatapp.response.ApiResponse;
import com.example.chatapp.response.UserResponse;
import com.example.chatapp.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "User", description = "使用者查詢與管理 API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(
        summary = "依帳號查詢使用者",
        description = "透過 username 查詢單一使用者的基本資料"
    )
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<UserResponse>> getUserByUsername(@RequestParam String username) {
        UserResponse response = userService.getUserByUsername(username);
        return ApiResponseFactory.success(response, "查詢成功");
    }

    @Operation(
        summary = "取得所有使用者列表",
        description = "回傳系統中所有使用者的基本資料"
    )
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> listAllUsers() {
        List<UserResponse> users = userService.listAllUsers();
        return ApiResponseFactory.success(users, "查詢成功");
    }

}
