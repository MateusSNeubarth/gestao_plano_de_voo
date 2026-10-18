package com.projarc.trabalhofinal.adapter.in.rest.dto.malha.response;

import lombok.Builder;

@Builder
public record AeroviaResponse(
        String id,
        String codOrigem,
        String codDestino,
        Integer distanciaKm
) {
}
