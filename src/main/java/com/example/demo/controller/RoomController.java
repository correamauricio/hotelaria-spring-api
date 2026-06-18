package com.example.demo.controller;

import com.example.demo.dto.RoomDetailsDTO;
import com.example.demo.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<List<RoomDetailsDTO>> listAll(@RequestParam(required = false) String status) {
        return ResponseEntity.ok(roomService.getAllRooms(status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDetailsDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(roomService.getRoomDetails(id));
    }
}
