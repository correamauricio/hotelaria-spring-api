package com.example.demo.repository;

import com.example.demo.model.Reservation;
import com.example.demo.model.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findFirstByRoomIdAndStatus(Long roomId, ReservationStatus status);

    @Query("SELECT COUNT(r) > 0 FROM Reservation r WHERE r.room.id = :roomId AND r.status <> :status AND r.expectedCheckInDate <= :checkOut AND r.expectedCheckOutDate >= :checkIn")
    boolean hasDateConflict(@Param("roomId") Long roomId, @Param("status") ReservationStatus status, @Param("checkIn") LocalDate checkIn, @Param("checkOut") LocalDate checkOut);
}
