package com.example.demo.service;

import com.example.demo.dto.GuestRequestDTO;
import com.example.demo.dto.GuestResponseDTO;
import java.util.List;

public interface GuestService {
    GuestResponseDTO createGuest(GuestRequestDTO guestRequestDTO);
    List<GuestResponseDTO> getGuests(String name);
}
