package com.example.chatapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.chatapp.entity.Room;
import com.example.chatapp.entity.RoomMember;

@Repository
public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {

    List<RoomMember> findByRoomId(Long roomId);

    List<RoomMember> findByUserId(Long userId);

    boolean existsByRoomIdAndUserId(Long roomId, Long userId);

    @Query("select rm.room from RoomMember rm where rm.user.id = :userId")
    List<Room> findRoomsByUserId(@Param("userId") Long userId);
}
