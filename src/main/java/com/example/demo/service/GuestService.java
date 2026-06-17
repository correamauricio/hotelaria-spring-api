package com.example.demo.service;

import com.example.demo.dto.GuestRequestDTO;
import com.example.demo.dto.GuestResponseDTO;
import com.example.demo.model.Guest;
import com.example.demo.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuestService {

    private final GuestRepository guestRepository;

    @Autowired
    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    @Transactional
    public GuestResponseDTO createGuest(GuestRequestDTO guestRequestDTO) {
        if (guestRepository.existsByDocumentNumber(guestRequestDTO.getCpf())) {
            throw new IllegalArgumentException("CPF already registered.");
        }
        
        if (guestRepository.existsByEmail(guestRequestDTO.getEmail())) {
            throw new IllegalArgumentException("Email already registered.");
        }

        Guest guest = new Guest();
        guest.setFullName(guestRequestDTO.getName());
        guest.setDocumentNumber(guestRequestDTO.getCpf());
        guest.setEmail(guestRequestDTO.getEmail());
        guest.setBirthDate(guestRequestDTO.getBirthDate());
        guest.setCreatedAt(LocalDateTime.now());

        Guest savedGuest = guestRepository.save(guest);
        
        return new GuestResponseDTO(savedGuest);
    }

    @Transactional(readOnly = true)
    public List<GuestResponseDTO> getGuests(String name) {
        List<Guest> guests;
        
        if (name != null && !name.trim().isEmpty()) {
            guests = guestRepository.findByFullNameContainingIgnoreCase(name);
        } else {
            guests = guestRepository.findAll();
        }
        
        return guests.stream()
                .map(GuestResponseDTO::new)
                .collect(Collectors.toList());
    }
}
