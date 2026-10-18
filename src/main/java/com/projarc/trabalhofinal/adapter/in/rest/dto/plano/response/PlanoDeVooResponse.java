package com.projarc.trabalhofinal.adapter.in.rest.dto.plano.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record PlanoDeVooResponse(
        Integer id,
        String status,
        LocalDateTime dataHoraPartida,
        Integer pilotoId,
        String pilotoNome,
        String aeronavePrefixo,
        String codOrigem,
        String codDestino,
        List<TrechoResponse> trechos
) {
}
