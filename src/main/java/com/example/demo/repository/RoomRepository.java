package com.example.demo.repository;

import com.example.demo.model.Rooms;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository
        extends JpaRepository<Rooms, Long> {

}
