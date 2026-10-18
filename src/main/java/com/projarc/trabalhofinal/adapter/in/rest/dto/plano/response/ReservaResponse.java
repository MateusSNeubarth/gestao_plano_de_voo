package com.projarc.trabalhofinal.adapter.in.rest.dto.plano.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ReservaResponse(
        Integer id,
        String tipo,
        LocalDateTime slot,
        String aeroviaId,
        Integer altitude,
        String codigoAeroporto,
        Integer numeroVaga
) {
}
