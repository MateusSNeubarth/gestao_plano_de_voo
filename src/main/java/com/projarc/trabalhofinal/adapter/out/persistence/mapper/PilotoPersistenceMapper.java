package com.projarc.trabalhofinal.adapter.out.persistence.mapper;

import com.projarc.trabalhofinal.adapter.out.persistence.entity.plano.PilotoEntity;
import com.projarc.trabalhofinal.domain.model.plano.Piloto;

public final class PilotoPersistenceMapper {
    private PilotoPersistenceMapper() {}

    public static Piloto toDomain(PilotoEntity entity) {
        return Piloto.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .build();
    }

    public static PilotoEntity toEntity(Piloto piloto) {
        return PilotoEntity.builder()
                .id(piloto.getId())
                .nome(piloto.getNome())
                .build();
    }
}