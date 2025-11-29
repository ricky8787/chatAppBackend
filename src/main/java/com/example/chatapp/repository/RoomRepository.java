package com.example.chatapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.chatapp.entity.Room;


@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {


}
