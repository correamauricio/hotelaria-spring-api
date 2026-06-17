package com.example.demo.repository;

import com.example.demo.model.Room;
import com.example.demo.model.enums.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByStatus(RoomStatus status);
}
