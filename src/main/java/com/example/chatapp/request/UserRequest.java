package com.example.chatapp.request;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;
    private String nickname;
}