package com.example.demo.service;

import com.example.demo.dto.RoomSummaryDTO;
import com.example.demo.dto.RoomDetailsDTO;
import com.example.demo.model.Room;
import com.example.demo.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<RoomSummaryDTO> getAllRooms(String status) {
        List<Room> rooms;
        if (status != null && !status.trim().isEmpty()) {
            rooms = roomRepository.findByStatusIgnoreCase(status);
        } else {
            rooms = roomRepository.findAll();
        }

        return rooms.stream()
                .map(room -> new RoomSummaryDTO(room.getId(), room.getNumber(), room.getStatus()))
                .collect(Collectors.toList());
    }

    public RoomDetailsDTO getRoomDetails(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "Quarto não encontrado"));

        return new RoomDetailsDTO(room.getId(), room.getNumber(), room.getStatus(), room.getBedCount());
    }
}
