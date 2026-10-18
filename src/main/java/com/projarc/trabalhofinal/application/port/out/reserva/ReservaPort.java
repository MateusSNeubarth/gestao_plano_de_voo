package com.projarc.trabalhofinal.application.port.out.reserva;

import com.projarc.trabalhofinal.domain.model.reserva.Reserva;
import com.projarc.trabalhofinal.domain.model.reserva.ReservaAerovia;
import com.projarc.trabalhofinal.domain.model.reserva.ReservaEstacionamento;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface ReservaPort {
    List<ReservaAerovia> getReservasDeAerovia(String aeroviaId, Collection<LocalDateTime> slots);
    List<ReservaEstacionamento> getReservasDeEstacionamento(String codigoAeroporto, Collection<LocalDateTime> slots);
    List<Reserva> createReservas(Integer planoId, List<Reserva> reservas);
    void deleteReservas(Integer planoId);
}
