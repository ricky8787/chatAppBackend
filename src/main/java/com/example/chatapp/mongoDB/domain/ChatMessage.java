package com.example.chatapp.mongoDB.domain;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "chat_messages")
public class ChatMessage {

    @Id
    private String id;

    // 房間 ID（例如: "room-1"、"general"）
    private String roomId;

    // 發訊者（之後可以改成 userId）
    private String userId;

    // 訊息內容
    private String content;

    // 建立時間
    private Instant createdAt = Instant.now();
}