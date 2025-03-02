package com.example.chatapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.chatapp.model.Room;
import com.example.chatapp.model.RoomMember;
import com.example.chatapp.model.User;
import com.example.chatapp.repository.RoomMemberRepository;
import com.example.chatapp.repository.RoomRepository;
import com.example.chatapp.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService {
	
	private final RoomRepository roomRepository;
	private final UserRepository userRepository;
	private final RoomMemberRepository roomMemberRepository;
	
	/**
	 * 建立一個群組聊天室
	 */
	public Room createGroupRoom(String name, List<String> members) {
		Room room = new Room();
		room.setName(name);
		room.setType(Room.RoomType.GROUP);
		room = roomRepository.save(room);
		
		for(String username : members) {
			addUserToRoom(room, username);
		}
		
		return room;
	}
	
	/**
     * 私聊聊天室：先檢查是否已有房間，沒有才新建
     */
    public Room getOrCreatePrivateRoom(String userA, String userB) {
        List<Room> existing = roomRepository.findPrivateRoomByUsers(userA, userB);
        if (!existing.isEmpty()) {
            return existing.get(0);
        }

        Room room = new Room();
        room.setName(userA + "_" + userB);
        room.setType(Room.RoomType.PRIVATE);
        room = roomRepository.save(room);

        addUserToRoom(room, userA);
        addUserToRoom(room, userB);

        return room;
    }

    private void addUserToRoom(Room room, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("找不到使用者: " + username));

        RoomMember member = new RoomMember();
        member.setRoom(room);
        member.setUser(user);
        roomMemberRepository.save(member);
    }

    public List<Room> getRoomsForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("找不到使用者: " + username));

        List<RoomMember> memberships = roomMemberRepository.findByUserId(user.getId());
        return memberships.stream()
                .map(RoomMember::getRoom)
                .collect(Collectors.toList());
    }
}
