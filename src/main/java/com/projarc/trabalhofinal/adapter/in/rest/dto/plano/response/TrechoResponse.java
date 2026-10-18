package com.projarc.trabalhofinal.adapter.in.rest.dto.plano.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record TrechoResponse(
        Integer ordem,
        String aeroviaId,
        Integer altitude,
        LocalDateTime horarioEntradaNaAerovia,
        LocalDateTime chegada,
        String acaoNaChegada,
        Integer numeroVagaPretendida,
        Integer tempoPermanenciaMinutos,
        LocalDateTime fimDaPermanencia
) {
}
