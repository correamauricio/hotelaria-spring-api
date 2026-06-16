package com.example.demo.controller;

import com.example.demo.dto.RoomSummaryDTO;
import com.example.demo.dto.RoomDetailsDTO;
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
    public List<RoomSummaryDTO> listAll(@RequestParam(required = false) String status) {
        return roomService.getAllRooms(status);
    }

    @GetMapping("/{id}")
    public RoomDetailsDTO findById(@PathVariable("id") Long id) {
        return roomService.getRoomDetails(id);
    }
}
