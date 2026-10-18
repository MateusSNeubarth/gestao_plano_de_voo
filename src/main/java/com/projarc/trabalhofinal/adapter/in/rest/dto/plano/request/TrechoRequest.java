package com.projarc.trabalhofinal.adapter.in.rest.dto.plano.request;

import com.projarc.trabalhofinal.domain.model.plano.AcaoNaChegada;

import java.time.LocalDateTime;

public record TrechoRequest(
        String aeroviaId,
        Integer altitude,
        LocalDateTime horarioEntradaNaAerovia,
        AcaoNaChegada acaoNaChegada,
        Integer numeroVagaPretendida,
        Integer tempoPermanenciaMinutos
) {
}
