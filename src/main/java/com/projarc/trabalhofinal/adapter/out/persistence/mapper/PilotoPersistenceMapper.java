package com.projarc.trabalhofinal.adapter.out.persistence.mapper;

import com.projarc.trabalhofinal.adapter.out.persistence.entity.PilotoEntity;
import com.projarc.trabalhofinal.domain.model.plano.Piloto;

public final class PilotoPersistenceMapper {
    private PilotoPersistenceMapper() {}

    public static Piloto toDomain(PilotoEntity entity) {
        return new Piloto(entity.getId(), entity.getNome());
    }

    public static PilotoEntity toEntity(Piloto piloto) {
        return PilotoEntity.builder()
                .id(piloto.getId())
                .nome(piloto.getNome())
                .build();
    }
}