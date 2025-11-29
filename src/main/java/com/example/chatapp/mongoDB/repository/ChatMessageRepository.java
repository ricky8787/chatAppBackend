package com.example.chatapp.mongoDB.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.chatapp.mongoDB.domain.ChatMessage;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    List<ChatMessage> findByRoomIdOrderByCreatedAtAsc(String roomId);
}