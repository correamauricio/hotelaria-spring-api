package com.example.demo.controller;

import com.example.demo.model.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.ReservaService;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;


    //Rota para criar uma nova RESERVA
    @PostMapping
    public ResponseEntity<?> criarNovaReserva(@RequestBody Reserva novaReserva){
       try{
           //Regra de conflito
           Reserva reservaSalva = reservaService.criarReserva(novaReserva);

           //devolve o JSON da reserva Status 201 (created)
           return ResponseEntity.status(HttpStatus.CREATED).body(reservaSalva);

       }catch (IllegalArgumentException | IllegalStateException e){

           //erro devolvendo com mensagem status 400 (bad request)
           return  ResponseEntity.badRequest().body(e.getMessage());
       }
    }

    @PostMapping("/{id}/checkin")
    public ResponseEntity<?> realizarCheckin(@PathVariable int id){
        try{

            Reserva reservaAtualizada = reservaService.fazerCheckIn(id);

            return ResponseEntity.ok(reservaAtualizada);

        }catch (IllegalArgumentException | IllegalStateException e){

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/checkout")
    public ResponseEntity<?> realizarCheckout(@PathVariable int id){
        try{
            Reserva reservaAtualizada = reservaService.fazerCheckOut(id);

            return ResponseEntity.ok(reservaAtualizada);

        }catch (IllegalArgumentException | IllegalStateException e){

            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping
    public List<Reserva> listarTodasReservas(){

        return reservaService.listaTodas();
    }
}
