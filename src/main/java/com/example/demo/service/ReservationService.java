package com.example.demo.service;

import com.example.demo.dto.ReservationRequestDTO;
import com.example.demo.model.Reservation;
import java.util.List;

public interface ReservationService {
    Reservation createReservation(ReservationRequestDTO dto);
    Reservation doCheckIn(Long id);
    Reservation doCheckOut(Long id);
    List<Reservation> listAll();
}
