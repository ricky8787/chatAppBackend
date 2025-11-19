package com.example.chatapp.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class UserRequest {
	@NotBlank(message = "Username cannot be empty")
    private String username;
	@NotBlank(message = "Password cannot be empty")
    private String password;
    private String nickname;
}