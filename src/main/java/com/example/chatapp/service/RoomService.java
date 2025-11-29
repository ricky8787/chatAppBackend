package com.example.chatapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.chatapp.entity.Room;
import com.example.chatapp.entity.RoomMember;
import com.example.chatapp.entity.User;
import com.example.chatapp.repository.RoomMemberRepository;
import com.example.chatapp.repository.RoomRepository;
import com.example.chatapp.repository.UserRepository;
import com.example.chatapp.request.CreateRoomRequest;
import com.example.chatapp.response.RoomResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final RoomMemberRepository roomMemberRepository;

    private final UserService userService;

    /**
     * 建立一個新的聊天室房間，並將指定的使用者加入為房間成員。
     *
     * 流程： 1. 新增 Room 實體（房間基本資訊） 2. 儲存 Room 取得主鍵（id） 3. 若請求中提供 memberIds，逐一新增
     * RoomMember 建立房間與使用者的關聯
     *
     * 注意： - Room 的 createdAt 由 @PrePersist 自動設定 - RoomMember 只會儲存 room_id 與
     * user_id（不會存放敏感資訊） - 若成員數量很多，可考慮使用 saveAll() 以提升效能
     *
     * @param req 建立房間的請求物件
     * @return 建立完成的 Room（包含 DB 產生的 id）
     */
    public Room createRoom(CreateRoomRequest req) {
        // 建立房間
        Room room = new Room();
        room.setRoomName(req.getRoomName());
        room.setAvatarUrl(req.getAvatarUrl());
        room.setIsPublic(req.getIsPublic());
        // createdAt 由 @PrePersist 自動填入
        Room savedRoom = roomRepository.save(room);

        // 加入房間成員
        if (req.getMemberIds() != null) {
            for (Long userId : req.getMemberIds()) {
                RoomMember member = new RoomMember();
                // 設定房間（room_id）
                member.setRoom(savedRoom);
                // ✔ 用 JPA getReferenceById（Lazy Proxy，不會立即查 DB）更省效能
                member.setUser(userRepository.getReferenceById(userId));
                // 儲存成員關聯
                roomMemberRepository.save(member);
            }
        }

        return savedRoom;
    }

    public List<RoomResponse> getMyRooms() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();   // JwtAuthenticationFilter 放進去的
        Long userId = currentUser.getId();

        List<Room> rooms = roomMemberRepository.findRoomsByUserId(userId);

        return rooms.stream()
                .map(RoomResponse::from)
                .collect(Collectors.toList());
    }

}
