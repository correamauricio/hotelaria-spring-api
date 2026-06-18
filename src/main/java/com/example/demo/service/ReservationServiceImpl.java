package com.example.demo.service;

import com.example.demo.dto.ReservationRequestDTO;
import com.example.demo.model.Guest;
import com.example.demo.model.Reservation;
import com.example.demo.model.Room;
import com.example.demo.model.enums.ReservationStatus;
import com.example.demo.repository.GuestRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.RoomUnavailableException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final GuestRepository guestRepository;
    private final RoomRepository roomRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, GuestRepository guestRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.guestRepository = guestRepository;
        this.roomRepository = roomRepository;
    }

    public Reservation createReservation(ReservationRequestDTO dto){

        Guest guest = guestRepository.findById(dto.getGuestId())
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found with ID: " + dto.getGuestId()));

        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: " + dto.getRoomId()));

        boolean occupiedRoom = reservationRepository.hasDateConflict(
                room.getId(),
                ReservationStatus.CANCELLED,
                dto.getCheckinDate(),
                dto.getCheckoutDate()
        );

        if(occupiedRoom){
            throw new RoomUnavailableException("The selected room is already occupied or reserved for this period");
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

    public Reservation doCheckIn(Long id){
       Reservation reservation = reservationRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with ID:" + id));

       reservation.doCheckIn();
       return reservationRepository.save(reservation);
    }

    public Reservation doCheckOut(Long id){
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with ID:" + id));

        reservation.doCheckOut();
        return reservationRepository.save(reservation);

    }

    public List<Reservation> listAll(){
        return reservationRepository.findAll();
    }
}
