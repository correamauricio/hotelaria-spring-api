package com.example.demo.service;

import com.example.demo.dto.RoomSummaryDTO;
import com.example.demo.dto.RoomDetailsDTO;
import com.example.demo.dto.CurrentGuestDTO;
import com.example.demo.model.Room;
import com.example.demo.model.Reservation;
import com.example.demo.model.Guest;
import com.example.demo.model.enums.RoomStatus;
import com.example.demo.model.enums.ReservationStatus;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    public RoomService(RoomRepository roomRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<RoomSummaryDTO> getAllRooms(String status) {
        List<Room> rooms;
        if (status != null && !status.trim().isEmpty()) {
            try {
                RoomStatus enumStatus = RoomStatus.valueOf(status.toUpperCase());
                rooms = roomRepository.findByStatus(enumStatus);
            } catch (IllegalArgumentException e) {
                throw new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.BAD_REQUEST, "Invalid room status");
            }
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

        RoomDetailsDTO dto = new RoomDetailsDTO(room.getId(), room.getNumber(), room.getStatus(), room.getBedCount());

        if (room.getStatus() == RoomStatus.OCCUPIED) {
            Optional<Reservation> reservationOpt = reservationRepository.findFirstByRoomIdAndStatus(room.getId(), ReservationStatus.IN_PROGRESS);
            if (reservationOpt.isPresent()) {
                Reservation reservation = reservationOpt.get();
                Guest guest = reservation.getGuest();
                if (guest != null) {
                    dto.setCurrentGuest(new CurrentGuestDTO(guest.getFullName(), reservation.getExpectedCheckOutDate()));
                }
            }
        }

        return dto;
    }
}
