package com.example.chatapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.chatapp.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("""
        SELECT r FROM Room r 
        JOIN RoomMember m1 ON r.id = m1.room.id
        JOIN RoomMember m2 ON r.id = m2.room.id
        WHERE r.type = 'PRIVATE' 
        AND m1.user.username = :userA 
        AND m2.user.username = :userB
    """)
    List<Room> findPrivateRoomByUsers(@Param("userA") String userA, @Param("userB") String userB);

}
