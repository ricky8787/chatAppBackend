package com.example.chatapp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.chatapp.mongoDB.domain.ChatMessage;
import com.example.chatapp.service.ChatMessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Chat Message", description = "聊天室訊息相關 API")
@RestController
@RequestMapping("/api/chatMessage")
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @Operation(
        summary = "取得聊天室歷史訊息",
        description = "依照 roomId 取得該聊天室的歷史訊息，依建立時間由舊到新排序"
    )
    @GetMapping("/history")
    public List<ChatMessage> history(@RequestParam String roomId) {
        return chatMessageService.getHistory(roomId);
    }


    @Operation(
            summary = "傳送聊天訊息",
            description = "在指定聊天室中發送一則訊息，並將訊息存入 MongoDB"
    )
    @PostMapping("/send")
    public ChatMessage create(@RequestBody ChatMessage req) {
        return chatMessageService.createMessage(
                req.getRoomId(),
                req.getUserId(),
                req.getContent());
    }

    
}
