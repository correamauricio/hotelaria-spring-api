package com.example.demo.controller;

import com.example.demo.dto.GuestRequestDTO;
import com.example.demo.dto.GuestResponseDTO;
import com.example.demo.service.GuestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/guests")
public class GuestController {

    private final GuestService guestService;

    @Autowired
    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping
    public ResponseEntity<List<GuestResponseDTO>> getGuests(@RequestParam(required = false) String name) {
        List<GuestResponseDTO> guests = guestService.getGuests(name);
        return ResponseEntity.ok(guests);
    }

    @PostMapping
    public ResponseEntity<GuestResponseDTO> createGuest(@Valid @RequestBody GuestRequestDTO guestRequestDTO) {
        GuestResponseDTO createdGuest = guestService.createGuest(guestRequestDTO);
        return new ResponseEntity<>(createdGuest, HttpStatus.CREATED);
    }
}
