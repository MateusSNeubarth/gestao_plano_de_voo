package com.projarc.trabalhofinal.application.usecase.reserva;

import com.projarc.trabalhofinal.application.port.out.reserva.ReservaPort;
import com.projarc.trabalhofinal.domain.model.plano.PlanoDeVoo;
import com.projarc.trabalhofinal.domain.model.plano.Problema;
import com.projarc.trabalhofinal.domain.model.plano.VerificacaoDoPlano;
import com.projarc.trabalhofinal.domain.model.reserva.Reserva;
import com.projarc.trabalhofinal.domain.model.reserva.ReservaAerovia;
import com.projarc.trabalhofinal.domain.model.reserva.ReservaEstacionamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VerificacaoDeDisponibilidade implements VerificacaoDoPlano {

    private final ReservaPort reservaPort;

    @Override
    public List<Problema> verificar(PlanoDeVoo plano) {
        var necessarias = plano.reservasNecessarias();
        List<Problema> problemas = new ArrayList<>();

        necessarias.stream()
                .filter(ReservaAerovia.class::isInstance)
                .map(ReservaAerovia.class::cast)
                .collect(Collectors.groupingBy(reserva -> reserva.getAerovia().getId()))
                .forEach((aeroviaId, reservas) -> {
                    var ocupadas = reservaPort.getReservasDeAerovia(aeroviaId, slots(reservas));
                    reservas.stream()
                            .filter(reserva -> ocupadas.stream().anyMatch(ocupada ->
                                    ocupada.getAltitude().equals(reserva.getAltitude()) && ocupada.getSlot().equals(reserva.getSlot())))
                            .forEach(reserva -> problemas.add(new Problema("CONFLITO_EM_AEROVIA",
                                    "Altitude %d da aerovia %s já reservada no slot %s"
                                            .formatted(reserva.getAltitude(), aeroviaId, reserva.getSlot()))));
                });

        necessarias.stream()
                .filter(ReservaEstacionamento.class::isInstance)
                .map(ReservaEstacionamento.class::cast)
                .collect(Collectors.groupingBy(reserva -> reserva.getAeroporto().getCodigo()))
                .forEach((codigoAeroporto, reservas) -> {
                    var ocupadas = reservaPort.getReservasDeEstacionamento(codigoAeroporto, slots(reservas));
                    reservas.stream()
                            .filter(reserva -> ocupadas.stream().anyMatch(ocupada ->
                                    ocupada.getNumeroVaga().equals(reserva.getNumeroVaga()) && ocupada.getSlot().equals(reserva.getSlot())))
                            .forEach(reserva -> problemas.add(new Problema("VAGA_INDISPONIVEL",
                                    "Vaga %d do aeroporto %s já reservada no slot %s"
                                            .formatted(reserva.getNumeroVaga(), codigoAeroporto, reserva.getSlot()))));
                });

        return problemas;
    }

    private List<LocalDateTime> slots(List<? extends Reserva> reservas) {
        return reservas.stream().map(Reserva::getSlot).distinct().toList();
    }
}
