package com.example.demo.service;

import com.example.demo.dto.RoomDetailsDTO;
import com.example.demo.dto.RoomSummaryDTO;
import java.util.List;

public interface RoomService {
    List<RoomSummaryDTO> getAllRooms(String status);
    RoomDetailsDTO getRoomDetails(Long id);
}
