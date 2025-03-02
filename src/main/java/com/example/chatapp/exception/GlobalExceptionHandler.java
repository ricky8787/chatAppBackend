package com.example.chatapp.exception;


import com.example.chatapp.common.ApiResponseFactory;
import com.example.chatapp.common.ErrorCode;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleBadRequest(IllegalArgumentException ex) {
        return ApiResponseFactory.error(ErrorCode.INVALID_PARAMETER, ex.getMessage());
    }

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<?> handleRoomNotFound(RoomNotFoundException ex) {
        return ApiResponseFactory.error(ErrorCode.ROOM_NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(AccessDeniedException ex) {
        return ApiResponseFactory.error(ErrorCode.ACCESS_DENIED, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleSystemError(Exception ex) {
        return ApiResponseFactory.error(ErrorCode.SYSTEM_ERROR);
    }
}