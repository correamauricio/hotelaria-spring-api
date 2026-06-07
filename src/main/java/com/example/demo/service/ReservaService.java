package com.example.demo.service;

import com.example.demo.model.Reserva;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservaService {

    //lista simulada para reservas temporarias;
    //TODO: trocar de lista para as entidade depois que o banco estiverer pronto.
    private final List<Reserva> reservaMockadas = new ArrayList<>();

    public Reserva criarReserva(Reserva novaReserva){

        //validação da data-saida "deve ser anterior a data de entrada"
        if(novaReserva.getDataSaida().isBefore(novaReserva.getDataEntrada())){
            throw new IllegalArgumentException(("A data de saída não pode ser anterior à data de entrada"));
        }

        //verifica conflitos de datas no quarto
        boolean quartoOcupado = verificarConflito(
                novaReserva.getQuartoId(),
                novaReserva.getDataEntrada(),
                novaReserva.getDataSaida()
        );

        if(quartoOcupado){
            throw new IllegalStateException("O quarto escolhido já esta ocupado ou reservado para o período selecionado");
        }

        //passou na validação adiciona o status "PENDENTE"
        //TODO: trocar de lista para as entidade depois que o banco estiverer pronto.
        novaReserva.setId((int) (reservaMockadas.size() + 1));
        novaReserva.setStatus("PENDENTE");
        reservaMockadas.add(novaReserva);

        return novaReserva;
    }

    private boolean verificarConflito(int quartoId, LocalDate inicioPretendido, LocalDate fimPretendido){
        //TODO: trocar de lista para as entidade depois que o banco estiverer pronto.
        for(Reserva reservaExistente : reservaMockadas){

            //verifica reservas do mesmo quartos que não foram canceladas
            if(reservaExistente.getQuartoId() == (quartoId)){

                boolean conflito = !inicioPretendido.isAfter(reservaExistente.getDataSaida()) &&
                        !fimPretendido.isBefore(reservaExistente.getDataEntrada());

                if(conflito){
                    return true;
                }
            }
        }
        return false;
    }

    public Reserva fazerCheckIn(int id){
        //TODO: trocar de lista para as entidade depois que o banco estiverer pronto.
        for (Reserva reserva : reservaMockadas){
            if(reserva.getId() == (id)){
                if(!reserva.getStatus().equals("PENDENTE")){
                    throw new IllegalStateException("o check in só pode ser feito em reservas PENDENTES");
                }
                reserva.setStatus("ANDAMENTO");
                return reserva;
            }
        }
        throw new IllegalArgumentException("Reserva não encontrada com o ID informado");
    }

    public Reserva fazerCheckOut(int id){
        //TODO: trocar de lista para as entidade depois que o banco estiverer pronto.
        for (Reserva reserva : reservaMockadas){
            if(reserva.getId() == (id)){
                if(!reserva.getStatus().equals("ANDAMENTO")){
                    throw new IllegalStateException("O check out só pode ser feito em reservas em ANDAMENTO");
                }
                reserva.setStatus("CONCLUIDA");
                return reserva;
            }
        }
        throw new IllegalArgumentException("Reserva não encontrada com o ID informado");
    }

    public List<Reserva> listaTodas(){
        return reservaMockadas;
    }
}
