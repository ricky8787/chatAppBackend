package com.example.chatapp.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.chatapp.mongoDB.domain.ChatMessage;
import com.example.chatapp.service.ChatMessageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChatMessageController {
	private final ChatMessageService chatMessageService;

    @PostMapping("/api/chatMessage/send")
    public ChatMessage create(@RequestBody ChatMessage req) {
        return chatMessageService.createMessage(
                req.getRoomId(),
                req.getUserId(),
                req.getContent()
        );
    }
   
}