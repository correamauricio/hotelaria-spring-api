package com.example.demo.controller;

import com.example.demo.dto.RoomDTO;
import com.example.demo.service.RoomService;
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
    public List<RoomDTO> listAll(@RequestParam(required = false) String status) {
        return roomService.getAllRooms(status);
    }

    @GetMapping("/{id}")
    public RoomDTO findById(@PathVariable("id") Long id) {
        return roomService.getRoomDetails(id);
    }
}
