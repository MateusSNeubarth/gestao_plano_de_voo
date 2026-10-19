package com.projarc.trabalhofinal.application.usecase.reserva;

import com.projarc.trabalhofinal.application.port.in.reserva.DisponibilidadeInPort;
import com.projarc.trabalhofinal.application.port.out.reserva.ReservaPort;
import com.projarc.trabalhofinal.domain.model.malha.Aeroporto;
import com.projarc.trabalhofinal.domain.model.malha.Aerovia;
import com.projarc.trabalhofinal.domain.model.malha.Altitudes;
import com.projarc.trabalhofinal.domain.model.reserva.ReservaAerovia;
import com.projarc.trabalhofinal.domain.model.reserva.ReservaEstacionamento;
import com.projarc.trabalhofinal.domain.model.reserva.Slot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class DisponibilidadeUseCase implements DisponibilidadeInPort {

    private final ReservaPort reservaPort;

    @Override
    public List<Integer> getAltitudesDisponiveis(Aerovia aerovia, LocalDateTime inicio, LocalDateTime fim) {
        var ocupadas = reservaPort.getReservasDeAerovia(aerovia.getId(), slots(inicio, fim)).stream()
                .map(ReservaAerovia::getAltitude)
                .collect(Collectors.toSet());
        return Altitudes.todas().stream()
                .filter(altitude -> !ocupadas.contains(altitude))
                .toList();
    }

    @Override
    public List<Integer> getVagasDisponiveis(Aeroporto aeroporto, LocalDateTime inicio, LocalDateTime fim) {
        var ocupadas = reservaPort.getReservasDeEstacionamento(aeroporto.getCodigo(), slots(inicio, fim)).stream()
                .map(ReservaEstacionamento::getNumeroVaga)
                .collect(Collectors.toSet());
        return IntStream.rangeClosed(1, aeroporto.getQtdVagas())
                .boxed()
                .filter(vaga -> !ocupadas.contains(vaga))
                .toList();
    }

    private List<LocalDateTime> slots(LocalDateTime inicio, LocalDateTime fim) {
        return Slot.tocadosPor(inicio, fim).stream().map(Slot::inicio).toList();
    }
}
