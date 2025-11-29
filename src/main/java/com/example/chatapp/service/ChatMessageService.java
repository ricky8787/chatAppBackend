package com.example.chatapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.chatapp.mongoDB.domain.ChatMessage;
import com.example.chatapp.mongoDB.repository.ChatMessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
	private final ChatMessageRepository messageRepository;

	public ChatMessage createMessage(String roomId, String userId, String content) {
		ChatMessage msg = new ChatMessage();
		msg.setRoomId(roomId);
		msg.setUserId(userId);
		msg.setContent(content);
		return messageRepository.save(msg);
	}

	public List<ChatMessage> getHistory(String roomId) {
		return messageRepository.findByRoomIdOrderByCreatedAtAsc(roomId);
	}
}