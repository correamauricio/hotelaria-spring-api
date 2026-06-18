package com.example.demo.service;

import com.example.demo.dto.RoomDetailsDTO;
import java.util.List;

public interface RoomService {
    List<RoomDetailsDTO> getAllRooms(String status);
    RoomDetailsDTO getRoomDetails(Long id);
}
