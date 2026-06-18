package com.example.demo.controller;

import com.example.demo.dto.ReservationRequestDTO;
import com.example.demo.dto.ReservationResponseDTO;
import com.example.demo.model.Reservation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.ReservationService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDTO> createReservation(@RequestBody ReservationRequestDTO newReservationDTO){
        Reservation savedReservation = reservationService.createReservation(newReservationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ReservationResponseDTO(savedReservation));
    }

    @PostMapping("/{id}/checkin")
    public ResponseEntity<ReservationResponseDTO> doCheckIn(@PathVariable Long id){
        Reservation updateReservation = reservationService.DoCheckIn(id);
        return ResponseEntity.ok(new ReservationResponseDTO(updateReservation));
    }

    @PostMapping("/{id}/checkout")
    public ResponseEntity<ReservationResponseDTO> doCheckOut(@PathVariable Long id){
        Reservation updateReservation = reservationService.DoCheckOut(id);
        return ResponseEntity.ok(new ReservationResponseDTO(updateReservation));
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDTO>> listAll(){
        List<ReservationResponseDTO> responseList = reservationService.listAll().stream()
                .map(ReservationResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }
}
