package com.example.demo.service;

import com.example.demo.model.Reservation;
import com.example.demo.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public Reservation createReservation(Reservation newReservation){

        //validação da data-saida "deve ser anterior a data de entrada"
        if(newReservation.getExpectedCheckOutDate().isBefore(newReservation.getExpectedCheckInDate())){
            throw new IllegalArgumentException(("Check-out date cannot be before Check-in date"));
        }

        //verifica conflitos de datas no quarto
        boolean occupiedRoom = checkConflict(
                newReservation.getRoomId(),
                newReservation.getExpectedCheckInDate(),
                newReservation.getExpectedCheckOutDate()
        );

        if(occupiedRoom){
            throw new IllegalStateException("The selected room is already occupied or reserved for this period");
        }

        //passou na validação adiciona o status "Scheduled"
        newReservation.setStatus("Scheduled");
        return reservationRepository.save(newReservation);
    }

    private boolean checkConflict(int roomId, LocalDate intendedStart, LocalDate intendedPurpose){

        List<Reservation> AllReservations = reservationRepository.findAll();

        for(Reservation existingReservation : AllReservations){

            //verifica reservas do mesmo quartos que não foram canceladas
            if(existingReservation.getRoomId() == roomId && !existingReservation.getStatus().equals("Cancelled") ){

                boolean conflict = !intendedStart.isAfter(existingReservation.getExpectedCheckOutDate()) &&
                        !intendedPurpose.isBefore(existingReservation.getExpectedCheckInDate());

                if(conflict){
                    return true;
                }
            }
        }
        return false;
    }

    public Reservation DoCheckIn(Long id){
       Reservation reservation = reservationRepository.findById(id)
               .orElseThrow(() -> new IllegalArgumentException("Reservation not found with ID:" + id));

       if (!reservation.getStatus().equals("Scheduled")){
           throw new IllegalStateException("Check-in can only be made for 'Scheduled' reservations");
       }

       reservation.setStatus("Checked-in");
       return reservationRepository.save(reservation);
    }

    public Reservation DoCheckOut(Long id){
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found with ID:" + id));

        if(!reservation.getStatus().equals("Checked-in")){
            throw new IllegalStateException("Check-out can only be made for 'Checked-in' reservations.");
        }

        reservation.setStatus("Checked-out");
        return reservationRepository.save(reservation);

    }

    public List<Reservation> listAll(){
        return reservationRepository.findAll();
    }
}
