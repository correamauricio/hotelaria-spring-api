package com.example.demo.controller;

import com.example.demo.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;


    //Rota para criar uma nova RESERVA
    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody Reservation newReservation){
       try{
           //Regra de conflito
           Reservation savedReservation = reservationService.createReservation(newReservation);

           //devolve o JSON da reserva Status 201 (created)
           return ResponseEntity.status(HttpStatus.CREATED).body(savedReservation);

       }catch (IllegalArgumentException | IllegalStateException e){

           //erro devolvendo com mensagem status 400 (bad request)
           return  ResponseEntity.badRequest().body(e.getMessage());
       }
    }

    @PostMapping("/{id}/checkin")
    public ResponseEntity<?> doCheckIn(@PathVariable Long id){
        try{
            Reservation updateReservation = reservationService.DoCheckIn(id);
            return ResponseEntity.ok(updateReservation);

        }catch (IllegalArgumentException | IllegalStateException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/checkout")
    public ResponseEntity<?> doCheckOut(@PathVariable Long id){
        try{
            Reservation updateReservation = reservationService.DoCheckOut(id);

            return ResponseEntity.ok(updateReservation);

        }catch (IllegalArgumentException | IllegalStateException e){

            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> listAll(){
        return ResponseEntity.ok(reservationService.listAll());
    }
}
