package com.projarc.trabalhofinal.application.port.in.reserva;

import com.projarc.trabalhofinal.domain.model.malha.Aeroporto;
import com.projarc.trabalhofinal.domain.model.malha.Aerovia;

import java.time.LocalDateTime;
import java.util.List;

public interface DisponibilidadeInPort {
    List<Integer> getAltitudesDisponiveis(Aerovia aerovia, LocalDateTime inicio, LocalDateTime fim);
    List<Integer> getVagasDisponiveis(Aeroporto aeroporto, LocalDateTime inicio, LocalDateTime fim);
}
