package com.projarc.trabalhofinal.adapter.out.persistence.mapper;

import com.projarc.trabalhofinal.adapter.out.persistence.entity.plano.PlanoDeVooEntity;
import com.projarc.trabalhofinal.adapter.out.persistence.entity.plano.TrechoEntity;
import com.projarc.trabalhofinal.domain.model.plano.Percurso;
import com.projarc.trabalhofinal.domain.model.plano.PlanoDeVoo;
import com.projarc.trabalhofinal.domain.model.plano.Trecho;

import java.util.ArrayList;

public final class PlanoPersistenceMapper {
    private PlanoPersistenceMapper() {}

    public static PlanoDeVoo toDomain(PlanoDeVooEntity entity) {
        return PlanoDeVoo.builder()
                .id(entity.getId())
                .status(entity.getStatus())
                .dataHoraPartida(entity.getDataHoraPartida())
                .aeronave(AeronavePersistenceMapper.toDomain(entity.getAeronave()))
                .piloto(PilotoPersistenceMapper.toDomain(entity.getPiloto()))
                .origem(MalhaPersistenceMapper.toDomain(entity.getOrigem()))
                .destino(MalhaPersistenceMapper.toDomain(entity.getDestino()))
                .percurso(Percurso.builder()
                        .trechos(new ArrayList<>(entity.getTrechos().stream()
                                .map(PlanoPersistenceMapper::toDomain)
                                .toList()))
                        .build())
                .reservas(new ArrayList<>(entity.getReservas().stream()
                        .map(ReservaPersistenceMapper::toDomain)
                        .toList()))
                .build();
    }

    public static Trecho toDomain(TrechoEntity entity) {
        return Trecho.builder()
                .aerovia(MalhaPersistenceMapper.toDomain(entity.getAerovia()))
                .altitude(entity.getAltitude())
                .horarioEntradaNaAerovia(entity.getHorarioEntradaNaAerovia())
                .acaoNaChegada(entity.getAcaoNaChegada())
                .numeroVagaPretendida(entity.getNumeroVagaPretendida())
                .tempoPermanenciaMinutos(entity.getTempoPermanenciaMinutos())
                .build();
    }
}
