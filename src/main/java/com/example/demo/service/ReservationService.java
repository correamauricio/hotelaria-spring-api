package com.example.demo.service;

import com.example.demo.dto.ReservationRequestDTO;
import com.example.demo.model.Guest;
import com.example.demo.model.Reservation;
import com.example.demo.model.Room;
import com.example.demo.model.enums.ReservationStatus;
import com.example.demo.repository.GuestRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final GuestRepository guestRepository;
    private final RoomRepository roomRepository;

    public ReservationService(ReservationRepository reservationRepository, GuestRepository guestRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.guestRepository = guestRepository;
        this.roomRepository = roomRepository;
    }

    public Reservation createReservation(ReservationRequestDTO dto){

        if(dto.getCheckoutDate().isBefore(dto.getCheckinDate()) || dto.getCheckoutDate().isEqual(dto.getCheckinDate())){
            throw new IllegalArgumentException(("Check-out date must be after Check-in date"));
        }

        Guest guest = guestRepository.findById(dto.getGuestId())
                .orElseThrow(() -> new IllegalArgumentException("Guest not found with ID: " + dto.getGuestId()));

        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("Room not found with ID: " + dto.getRoomId()));

        boolean occupiedRoom = reservationRepository.hasDateConflict(
                room.getId(),
                ReservationStatus.CANCELLED,
                dto.getCheckinDate(),
                dto.getCheckoutDate()
        );

        if(occupiedRoom){
            throw new IllegalStateException("The selected room is already occupied or reserved for this period");
        }

        long days = ChronoUnit.DAYS.between(dto.getCheckinDate(), dto.getCheckoutDate());
        BigDecimal totalAmount = room.getBaseDailyRate() != null ? room.getBaseDailyRate().multiply(BigDecimal.valueOf(days)) : BigDecimal.ZERO;

        Reservation newReservation = new Reservation();
        newReservation.setGuest(guest);
        newReservation.setRoom(room);
        newReservation.setExpectedCheckInDate(dto.getCheckinDate());
        newReservation.setExpectedCheckOutDate(dto.getCheckoutDate());
        newReservation.setAppliedDailyRate(room.getBaseDailyRate() != null ? room.getBaseDailyRate() : BigDecimal.ZERO);
        newReservation.setTotalAmount(totalAmount);
        newReservation.setStatus(ReservationStatus.SCHEDULED);

        return reservationRepository.save(newReservation);
    }

    public Reservation DoCheckIn(Long id){
       Reservation reservation = reservationRepository.findById(id)
               .orElseThrow(() -> new IllegalArgumentException("Reservation not found with ID:" + id));

       if (reservation.getStatus() != ReservationStatus.SCHEDULED){
           throw new IllegalStateException("Check-in can only be made for 'Scheduled' reservations");
       }

       reservation.setStatus(ReservationStatus.IN_PROGRESS);
       return reservationRepository.save(reservation);
    }

    public Reservation DoCheckOut(Long id){
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found with ID:" + id));

        if(reservation.getStatus() != ReservationStatus.IN_PROGRESS){
            throw new IllegalStateException("Check-out can only be made for 'Checked-in' reservations.");
        }

        reservation.setStatus(ReservationStatus.COMPLETED);
        return reservationRepository.save(reservation);

    }

    public List<Reservation> listAll(){
        return reservationRepository.findAll();
    }
}
