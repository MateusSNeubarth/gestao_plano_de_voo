package com.projarc.trabalhofinal.adapter.in.rest.dto.malha.request;

public record AeroviaRequest(
        String id,
        String codOrigem,
        String codDestino,
        Integer distanciaKm
) {
}
