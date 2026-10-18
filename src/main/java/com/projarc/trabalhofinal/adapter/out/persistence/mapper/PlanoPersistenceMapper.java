package com.projarc.trabalhofinal.adapter.out.persistence.mapper;

import com.projarc.trabalhofinal.adapter.out.persistence.entity.plano.PilotoEntity;
import com.projarc.trabalhofinal.adapter.out.persistence.entity.plano.PlanoDeVooEntity;
import com.projarc.trabalhofinal.adapter.out.persistence.entity.plano.TrechoEntity;
import com.projarc.trabalhofinal.adapter.out.persistence.entity.reserva.ReservaEntity;
import com.projarc.trabalhofinal.adapter.out.persistence.entity.reserva.ReservaAeroviaEntity;
import com.projarc.trabalhofinal.adapter.out.persistence.entity.reserva.ReservaEstacionamentoEntity;

import com.projarc.trabalhofinal.domain.model.plano.Piloto;
import com.projarc.trabalhofinal.domain.model.plano.PlanoDeVoo;
import com.projarc.trabalhofinal.domain.model.plano.Trecho;
import com.projarc.trabalhofinal.domain.model.reserva.Reserva;
import com.projarc.trabalhofinal.domain.model.reserva.ReservaAerovia;
import com.projarc.trabalhofinal.domain.model.reserva.ReservaEstacionamento;
import com.projarc.trabalhofinal.domain.model.plano.Percurso;

import java.util.ArrayList;
import java.util.List;

public final class PlanoPersistenceMapper {

    private PlanoPersistenceMapper() {
    }


    public static PlanoDeVooEntity fromDomain(PlanoDeVoo planoDeVoo) {
        return PlanoDeVooEntity.builder()
                .id(planoDeVoo.getId())
                .status(planoDeVoo.getStatus())
                .dataHoraPartida(planoDeVoo.getDataHoraPartida())
                // Pendente: conversão da aeronave com o código do Bernardo.
                .aeronave(null)
                .piloto(fromDomain(planoDeVoo.getPiloto()))
                .origem(MalhaPersistenceMapper.fromDomain(planoDeVoo.getOrigem()))
                .destino(MalhaPersistenceMapper.fromDomain(planoDeVoo.getDestino()))
                .trechos(trechosFromDomain(
                        planoDeVoo.getPercurso() == null
                                ? null
                                : planoDeVoo.getPercurso().getTrechos()
                ))
                .reservas(reservasFromDomain(planoDeVoo.getReservas()))
                .build();
    }

    public static PlanoDeVoo toDomain(PlanoDeVooEntity planoDeVooEntity) {
        return PlanoDeVoo.builder()
                .id(planoDeVooEntity.getId())
                .status(planoDeVooEntity.getStatus())
                .dataHoraPartida(planoDeVooEntity.getDataHoraPartida())
                // Pendente: conversão da aeronave com o código do Bernardo.
                .aeronave(null)
                .piloto(toDomain(planoDeVooEntity.getPiloto()))
                .origem(MalhaPersistenceMapper.toDomain(planoDeVooEntity.getOrigem()))
                .destino(MalhaPersistenceMapper.toDomain(planoDeVooEntity.getDestino()))
                .percurso(Percurso.builder()
                        .trechos(trechosToDomain(planoDeVooEntity.getTrechos()))
                        .build())
                .reservas(reservasToDomain(planoDeVooEntity.getReservas()))
                .build();
    }

    public static PilotoEntity fromDomain(Piloto piloto) {
        if (piloto == null) {
            return null;
        }

        return PilotoEntity.builder()
                .id(piloto.getId())
                .nome(piloto.getNome())
                .build();
    }

    public static Piloto toDomain(PilotoEntity pilotoEntity) {
        if (pilotoEntity == null) {
            return null;
        }

        return Piloto.builder()
                .id(pilotoEntity.getId())
                .nome(pilotoEntity.getNome())
                .build();
    }

    public static List<TrechoEntity> trechosFromDomain(List<Trecho> trechos) {
        if (trechos == null) {
            return new ArrayList<>();
        }

        return trechos.stream()
                .map(PlanoPersistenceMapper::fromDomain)
                .toList();
    }

    public static List<Trecho> trechosToDomain(List<TrechoEntity> trechos) {
        if (trechos == null) {
            return new ArrayList<>();
        }

        return trechos.stream()
                .map(PlanoPersistenceMapper::toDomain)
                .toList();
    }

    public static TrechoEntity fromDomain(Trecho trecho) {
        if (trecho == null) {
            return null;
        }

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
        if (trechoEntity == null) {
            return null;
        }

        return Trecho.builder()
                .aerovia(MalhaPersistenceMapper.toDomain(trechoEntity.getAerovia()))
                .altitude(trechoEntity.getAltitude())
                .horarioEntradaNaAerovia(trechoEntity.getHorarioEntradaNaAerovia())
                .acaoNaChegada(trechoEntity.getAcaoNaChegada())
                .numeroVagaPretendida(trechoEntity.getNumeroVagaPretendida())
                .tempoPermanenciaMinutos(trechoEntity.getTempoPermanenciaMinutos())
                .build();
    }

    public static ReservaEntity fromDomain(Reserva reserva) {
        if (reserva == null) {
            return null;
        }

        if (reserva instanceof ReservaAerovia reservaAerovia) {
            return ReservaAeroviaEntity.builder()
                    .id(reservaAerovia.getId())
                    .slot(reservaAerovia.getSlot())
                    .aerovia(MalhaPersistenceMapper.fromDomain(
                            reservaAerovia.getAerovia()
                    ))
                    .altitude(reservaAerovia.getAltitude())
                    .build();
        }

        if (reserva instanceof ReservaEstacionamento reservaEstacionamento) {
            return ReservaEstacionamentoEntity.builder()
                    .id(reservaEstacionamento.getId())
                    .slot(reservaEstacionamento.getSlot())
                    .aeroporto(MalhaPersistenceMapper.fromDomain(
                            reservaEstacionamento.getAeroporto()
                    ))
                    .numeroVaga(reservaEstacionamento.getNumeroVaga())
                    .build();
        }

        throw new IllegalArgumentException(
                "Tipo de reserva não suportado: " + reserva.getClass().getName()
        );
    }

    public static List<ReservaEntity> reservasFromDomain(List<Reserva> reservas) {
        if (reservas == null) {
            return new ArrayList<>();
        }

        return reservas.stream()
                .map(PlanoPersistenceMapper::fromDomain)
                .toList();
    }

    public static Reserva toDomain(ReservaEntity reservaEntity) {
        if (reservaEntity == null) {
            return null;
        }

        if (reservaEntity instanceof ReservaAeroviaEntity reservaAerovia) {
            return ReservaAerovia.builder()
                    .id(reservaAerovia.getId())
                    .slot(reservaAerovia.getSlot())
                    .aerovia(MalhaPersistenceMapper.toDomain(
                            reservaAerovia.getAerovia()
                    ))
                    .altitude(reservaAerovia.getAltitude())
                    .build();
        }

        if (reservaEntity instanceof ReservaEstacionamentoEntity reservaEstacionamento) {
            return ReservaEstacionamento.builder()
                    .id(reservaEstacionamento.getId())
                    .slot(reservaEstacionamento.getSlot())
                    .aeroporto(MalhaPersistenceMapper.toDomain(
                            reservaEstacionamento.getAeroporto()
                    ))
                    .numeroVaga(reservaEstacionamento.getNumeroVaga())
                    .build();
        }

        throw new IllegalArgumentException(
                "Tipo de reserva não suportado: "
                        + reservaEntity.getClass().getName()
        );
    }

    public static List<Reserva> reservasToDomain(List<ReservaEntity> reservas) {
        if (reservas == null) {
            return new ArrayList<>();
        }

        return reservas.stream()
                .map(PlanoPersistenceMapper::toDomain)
                .toList();
    }
}
