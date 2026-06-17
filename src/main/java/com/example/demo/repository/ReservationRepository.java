package com.example.demo.repository;

import com.example.demo.model.Reservation;
import com.example.demo.model.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findFirstByRoomIdAndStatus(int roomId, ReservationStatus status);
}
