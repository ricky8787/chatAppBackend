package com.example.chatapp.service;

import lombok.Getter;

@Getter
public enum ErrorCode {

    SUCCESS("0000", "成功"),
    INVALID_PARAMETER("4000", "參數錯誤"),
    ROOM_NOT_FOUND("4040", "找不到房間"),
    ACCESS_DENIED("4030", "無權限"),
    SYSTEM_ERROR("5000", "系統錯誤");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
