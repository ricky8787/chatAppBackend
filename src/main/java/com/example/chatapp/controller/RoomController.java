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
//	@PostMapping("/group")
//	public ResponseEntity<ApiResponse<RoomResponse>> createGroupRoom(@RequestBody GroupRequest request){
//		 	Room room = roomService.createGroupRoom(request.getName(), request.getMembers());
//	        return ResponseEntity.ok(new ApiResponse<>("0000", "建立成功", response));
//	}
}

