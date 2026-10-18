package com.projarc.trabalhofinal.adapter.out.persistence.mapper;

import com.projarc.trabalhofinal.adapter.out.persistence.entity.plano.PlanoDeVooEntity;
import com.projarc.trabalhofinal.adapter.out.persistence.entity.plano.TrechoEntity;
import com.projarc.trabalhofinal.domain.model.plano.Percurso;
import com.projarc.trabalhofinal.domain.model.plano.PlanoDeVoo;
import com.projarc.trabalhofinal.domain.model.plano.Trecho;

import java.util.ArrayList;
import java.util.List;

public final class PlanoPersistenceMapper {
    private PlanoPersistenceMapper() {}

    public static PlanoDeVooEntity fromDomain(PlanoDeVoo planoDeVoo) {
        return PlanoDeVooEntity.builder()
                .id(planoDeVoo.getId())
                .status(planoDeVoo.getStatus())
                .dataHoraPartida(planoDeVoo.getDataHoraPartida())
                .aeronave(AeronavePersistenceMapper.toEntity(planoDeVoo.getAeronave()))
                .piloto(PilotoPersistenceMapper.toEntity(planoDeVoo.getPiloto()))
                .origem(MalhaPersistenceMapper.fromDomain(planoDeVoo.getOrigem()))
                .destino(MalhaPersistenceMapper.fromDomain(planoDeVoo.getDestino()))
                .trechos(trechosFromDomain(planoDeVoo.getPercurso().getTrechos()))
                .build();
    }

    public static PlanoDeVoo toDomain(PlanoDeVooEntity planoDeVooEntity) {
        return PlanoDeVoo.builder()
                .id(planoDeVooEntity.getId())
                .status(planoDeVooEntity.getStatus())
                .dataHoraPartida(planoDeVooEntity.getDataHoraPartida())
                .aeronave(AeronavePersistenceMapper.toDomain(planoDeVooEntity.getAeronave()))
                .piloto(PilotoPersistenceMapper.toDomain(planoDeVooEntity.getPiloto()))
                .origem(MalhaPersistenceMapper.toDomain(planoDeVooEntity.getOrigem()))
                .destino(MalhaPersistenceMapper.toDomain(planoDeVooEntity.getDestino()))
                .percurso(Percurso.builder()
                        .trechos(new ArrayList<>(planoDeVooEntity.getTrechos().stream()
                                .map(PlanoPersistenceMapper::toDomain)
                                .toList()))
                        .build())
                .reservas(new ArrayList<>(planoDeVooEntity.getReservas().stream()
                        .map(ReservaPersistenceMapper::toDomain)
                        .toList()))
                .build();
    }

    public static List<TrechoEntity> trechosFromDomain(List<Trecho> trechos) {
        return new ArrayList<>(trechos.stream()
                .map(PlanoPersistenceMapper::fromDomain)
                .toList());
    }

    public static TrechoEntity fromDomain(Trecho trecho) {
        return TrechoEntity.builder()
                .aerovia(MalhaPersistenceMapper.fromDomain(trecho.getAerovia()))
                .altitude(trecho.getAltitude())
                .horarioEntradaNaAerovia(trecho.getHorarioEntradaNaAerovia())
                .acaoNaChegada(trecho.getAcaoNaChegada())
                .numeroVagaPretendida(trecho.getNumeroVagaPretendida())
                .tempoPermanenciaMinutos(trecho.getTempoPermanenciaMinutos())
                .build();
    }

    public static Trecho toDomain(TrechoEntity trechoEntity) {
        return Trecho.builder()
                .aerovia(MalhaPersistenceMapper.toDomain(trechoEntity.getAerovia()))
                .altitude(trechoEntity.getAltitude())
                .horarioEntradaNaAerovia(trechoEntity.getHorarioEntradaNaAerovia())
                .acaoNaChegada(trechoEntity.getAcaoNaChegada())
                .numeroVagaPretendida(trechoEntity.getNumeroVagaPretendida())
                .tempoPermanenciaMinutos(trechoEntity.getTempoPermanenciaMinutos())
                .build();
    }
}
