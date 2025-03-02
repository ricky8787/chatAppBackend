package com.example.chatapp.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {
    private Long id;
    private String name;
    private String type;      // 如 "GROUP" 或 "PRIVATE"
    private List<String> members;  // 此房間中成員的使用者名稱
}
