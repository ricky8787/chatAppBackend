package com.example.chatapp.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.chatapp.model.Room;
import com.example.chatapp.response.ApiResponse;
import com.example.chatapp.response.RoomResponse;
import com.example.chatapp.service.RoomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {
	
	private final RoomService roomService;
	
	/**
	 * 新增聊天群組
	 */
	@PostMapping("/group")
	public ResponseEntity<ApiResponse<RoomResponse>> createGroupRoom(@RequestBody GroupRequest request){
		 	Room room = roomService.createGroupRoom(request.getName(), request.getMembers());
	        RoomResponse response = convertToRoomResponse(room);
	        return ResponseEntity.ok(new ApiResponse<>("0000", "建立成功", response));
	}
	
	@PostMapping("/private/{userA}/{userB}")
    public ResponseEntity<ApiResponse<RoomResponse>> getOrCreatePrivateRoom(@PathVariable String userA, @PathVariable String userB) {
        Room room = roomService.getOrCreatePrivateRoom(userA, userB);
        RoomResponse response = convertToRoomResponse(room);
        return ResponseEntity.ok(new ApiResponse<>("0000", "建立成功", response));
    }

    @GetMapping("/{username}")
    public ResponseEntity<ApiResponse<List<RoomResponse>>> getUserRooms(@PathVariable String username) {
        List<Room> rooms = roomService.getRoomsForUser(username);
        List<RoomResponse> responses = rooms.stream()
                .map(this::convertToRoomResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>("0000", "查詢成功", responses));
    }

    // DTO 轉換方法：根據 Room Entity 建立 RoomResponse
    private RoomResponse convertToRoomResponse(Room room) {
        // 假設 Room Entity 有 getId(), getName(), getType(), 以及 getMembers() 方法，
        // 而成員是 RoomMember 物件，RoomMember 有 getUser() 方法，User 有 getUsername()。
        List<String> members = room.getMembers() != null ?
            room.getMembers().stream()
                .map(member -> member.getUser().getUsername())
                .collect(Collectors.toList())
            : null;
        return new RoomResponse(room.getId(), room.getName(), room.getType().name(), members);
    }
}
