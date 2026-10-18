package com.projarc.trabalhofinal.adapter.in.rest.mapper;

import com.projarc.trabalhofinal.adapter.in.rest.dto.PilotoRequest;
import com.projarc.trabalhofinal.adapter.in.rest.dto.PilotoResponse;
import com.projarc.trabalhofinal.domain.model.plano.Piloto;

public final class PilotoRestMapper {
    private PilotoRestMapper() {}

    public static PilotoResponse toResponse(Piloto piloto) {
        return new PilotoResponse(piloto.getId(), piloto.getNome());
    }

    public static Piloto toDomain(PilotoRequest request) {
        return Piloto.builder()
                .nome(request.nome())
                .build();
    }
}