package com.projarc.trabalhofinal.adapter.in.rest.mapper.malha;

import com.projarc.trabalhofinal.adapter.in.rest.dto.malha.request.AeroportoRequest;
import com.projarc.trabalhofinal.adapter.in.rest.dto.malha.response.AeroportoResponse;
import com.projarc.trabalhofinal.domain.model.malha.Aeroporto;

public final class AeroportoRestMapper {

    private AeroportoRestMapper() {}

    public static AeroportoResponse fromDomain(Aeroporto aeroporto) {
        return AeroportoResponse.builder()
                .codigo(aeroporto.getCodigo())
                .nome(aeroporto.getNome())
                .qtdVagas(aeroporto.getQtdVagas())
                .build();
    }

    public static Aeroporto toDomain(AeroportoRequest request) {
        return Aeroporto.builder()
                .codigo(request.codigo())
                .nome(request.nome())
                .qtdVagas(request.qtdVagas())
                .build();
    }
}
