package com.projarc.trabalhofinal.adapter.in.rest.mapper;

import com.projarc.trabalhofinal.adapter.in.rest.dto.reserva.response.AltitudesDisponiveisResponse;
import com.projarc.trabalhofinal.adapter.in.rest.dto.reserva.response.VagasDisponiveisResponse;
import com.projarc.trabalhofinal.domain.model.malha.Aeroporto;
import com.projarc.trabalhofinal.domain.model.malha.Aerovia;

import java.time.LocalDateTime;
import java.util.List;

public final class ReservaRestMapper {

    private ReservaRestMapper() {}

    public static AltitudesDisponiveisResponse toAltitudesDisponiveis(Aerovia aerovia, LocalDateTime inicio, LocalDateTime fim, List<Integer> altitudes) {
        return AltitudesDisponiveisResponse.builder()
                .aeroviaId(aerovia.getId())
                .inicio(inicio)
                .fim(fim)
                .altitudes(altitudes)
                .build();
    }

    public static VagasDisponiveisResponse toVagasDisponiveis(Aeroporto aeroporto, LocalDateTime inicio, LocalDateTime fim, List<Integer> vagas) {
        return VagasDisponiveisResponse.builder()
                .codigoAeroporto(aeroporto.getCodigo())
                .inicio(inicio)
                .fim(fim)
                .vagas(vagas)
                .build();
    }
}
