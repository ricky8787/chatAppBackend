package com.example.chatapp.response;

import java.util.List;
import java.util.stream.Collectors;

import com.example.chatapp.entity.Room;
import com.example.chatapp.entity.RoomMember;
import com.example.chatapp.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {
    private Long id;
    private String name;
    private String type;      // "GROUP" or "PRIVATE"
    private List<String> members;  // 房間中的成員使用者名稱

    public static RoomResponse from(Room room) {
        RoomResponse response = new RoomResponse();
        response.setId(room.getId());
        response.setName(room.getRoomName());
        response.setType(Boolean.TRUE.equals(room.getIsPublic()) ? "GROUP" : "PRIVATE");

        List<String> memberUsernames = room.getMembers().stream()
                .map(RoomMember::getUser)
                .map(User::getUsername)
                .collect(Collectors.toList());
        response.setMembers(memberUsernames);

        return response;
    }
}
