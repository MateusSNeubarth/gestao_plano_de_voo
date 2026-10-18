package com.projarc.trabalhofinal.adapter.in.rest.dto.plano.response;

import lombok.Builder;

import java.util.List;

@Builder
public record SituacaoDoPlanoResponse(
        Integer id,
        String status,
        List<ReservaResponse> reservas
) {
}
