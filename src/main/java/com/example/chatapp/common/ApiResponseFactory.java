package com.example.chatapp.common;

import com.example.chatapp.response.ApiResponse;

import org.springframework.http.ResponseEntity;

public class ApiResponseFactory {

    public static <T> ResponseEntity<ApiResponse<T>> success(T data, String message) {
        return ResponseEntity.ok(new ApiResponse<>(ErrorCode.SUCCESS.getCode(), message, data));
    }

    public static ResponseEntity<ApiResponse<Void>> error(ErrorCode errorCode) {
        return ResponseEntity.badRequest().body(
            new ApiResponse<>(errorCode.getCode(), errorCode.getMessage(), null)
        );
    }

    public static ResponseEntity<ApiResponse<Void>> error(ErrorCode errorCode, String customMessage) {
        return ResponseEntity.badRequest().body(
            new ApiResponse<>(errorCode.getCode(), customMessage, null) 
        );
    }
}