package com.example.demo.controller;

import com.example.demo.model.Room;
import com.example.demo.repository.RoomRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/quartos")
public class RoomController {
    private final RoomRepository repository;

    public RoomController (RoomRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Room> listAll() {
        return repository.findAll();
    }

    
    @GetMapping("/{room_id}")
    public Room findById(@PathVariable("room_id") Long room_id) {
        return repository.findById(room_id)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "Quarto não encontrado"));
    }
}
