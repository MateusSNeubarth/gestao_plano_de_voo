package com.projarc.trabalhofinal.adapter.in.rest.dto.plano.request;

import java.time.LocalDateTime;

public record PlanoDeVooRequest(
        Integer pilotoId,
        String aeronavePrefixo,
        String codOrigem,
        String codDestino,
        LocalDateTime dataHoraPartida
) {
}
