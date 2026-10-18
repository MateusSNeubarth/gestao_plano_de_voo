package com.projarc.trabalhofinal.adapter.in.rest.dto.reserva.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record AltitudesDisponiveisResponse(
        String aeroviaId,
        LocalDateTime inicio,
        LocalDateTime fim,
        List<Integer> altitudes
) {
}
