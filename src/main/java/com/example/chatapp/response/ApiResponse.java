package com.example.chatapp.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
	private String code;      // 例如 "0000" 表示成功
    private String message;   // 說明訊息
    private T data;           // 回傳資料

}
