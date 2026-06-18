package com.example.demo.service;

import com.example.demo.dto.RoomDetailsDTO;
import com.example.demo.dto.CurrentGuestDTO;
import com.example.demo.model.Room;
import com.example.demo.model.Reservation;
import com.example.demo.model.Guest;
import com.example.demo.model.enums.RoomStatus;
import com.example.demo.model.enums.ReservationStatus;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.exception.BusinessValidationException;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    public RoomServiceImpl(RoomRepository roomRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<RoomDetailsDTO> getAllRooms(String status) {
        List<Room> rooms;
        if (status != null && !status.trim().isEmpty()) {
            try {
                RoomStatus enumStatus = RoomStatus.valueOf(status.toUpperCase());
                rooms = roomRepository.findByStatus(enumStatus);
            } catch (IllegalArgumentException e) {
                throw new BusinessValidationException("Invalid room status");
            }
        } else {
            rooms = roomRepository.findAll();
        }

        return rooms.stream()
                .map(this::mapToRoomDetails)
                .collect(Collectors.toList());
    }

    public RoomDetailsDTO getRoomDetails(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quarto não encontrado"));

        return mapToRoomDetails(room);
    }
    
    private RoomDetailsDTO mapToRoomDetails(Room room) {
        RoomDetailsDTO dto = new RoomDetailsDTO(room.getId(), room.getNumber(), room.getStatus(), room.getBedCount(), room.getBaseDailyRate());

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
