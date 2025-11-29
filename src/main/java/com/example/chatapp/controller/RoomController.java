package com.example.chatapp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.chatapp.entity.Room;
import com.example.chatapp.request.CreateRoomRequest;
import com.example.chatapp.response.RoomResponse;
import com.example.chatapp.service.RoomService;

import lombok.RequiredArgsConstructor;

/**
 * RoomController 處理所有與聊天室相關的 API 請求。
 * 基礎路徑為 /api/rooms。
 * 由於有 Spring Security 的設定，所有在此 Controller 中的 API 都需要經過認證 (authenticated)。
 */
@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    /**
     * 取得當前登入使用者所屬的所有聊天室列表。
     * 這個 API 會讀取 Security Context 中的使用者資訊，
     * 然後去資料庫查詢該使用者參與的所有房間。
     *
     * @return List<RoomResponse> 使用者所屬的聊天室列表
     */
    @GetMapping("/user-room")
    public List<RoomResponse> getMyRooms() {
        return roomService.getMyRooms();
    }

    /**
     * 建立一個新的聊天室。
     * @param request 包含聊天室名稱、成員ID列表等資訊的請求物件
     * @return RoomResponse 建立成功後回傳的聊天室資訊
     */
    @PostMapping
    public RoomResponse createRoom(@RequestBody CreateRoomRequest request) {
        Room room = roomService.createRoom(request);
        return RoomResponse.from(room);
    }
}
