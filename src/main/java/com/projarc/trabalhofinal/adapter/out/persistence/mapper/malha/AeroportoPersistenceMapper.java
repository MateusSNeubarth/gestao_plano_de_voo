package com.projarc.trabalhofinal.adapter.out.persistence.mapper.malha;

import com.projarc.trabalhofinal.adapter.out.persistence.entity.malha.AeroportoEntity;
import com.projarc.trabalhofinal.domain.model.malha.Aeroporto;

public final class AeroportoPersistenceMapper {
    private AeroportoPersistenceMapper() {
    }

    public static Aeroporto toDomain(AeroportoEntity aeronaveEntity) {
        return Aeroporto.builder()
                .codigo(aeronaveEntity.getCodigo())
                .nome(aeronaveEntity.getNome())
                .qtdVagas(aeronaveEntity.getQtdVagas())
                .build();
    }

    public static AeroportoEntity fromDomain(Aeroporto aeroporto) {
        return AeroportoEntity.builder()
                .codigo(aeroporto.getCodigo())
                .nome(aeroporto.getNome())
                .qtdVagas(aeroporto.getQtdVagas())
                .build();
    }
}
