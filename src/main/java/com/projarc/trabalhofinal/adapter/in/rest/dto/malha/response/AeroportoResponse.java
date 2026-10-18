package com.projarc.trabalhofinal.adapter.in.rest.dto.malha.response;

import lombok.Builder;

@Builder
public record AeroportoResponse(
        String codigo,
        String nome,
        Integer qtdVagas
) {
}
