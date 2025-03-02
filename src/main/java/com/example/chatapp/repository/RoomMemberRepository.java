package com.example.chatapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.chatapp.model.RoomMember;

@Repository
public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {

    @Query("SELECT rm FROM RoomMember rm WHERE rm.room.id = :roomId")
    List<RoomMember> findByRoomId(@Param("roomId") Long roomId);

    @Query("SELECT rm FROM RoomMember rm WHERE rm.user.id = :userId")
    List<RoomMember> findByUserId(@Param("userId") Long userId);
}