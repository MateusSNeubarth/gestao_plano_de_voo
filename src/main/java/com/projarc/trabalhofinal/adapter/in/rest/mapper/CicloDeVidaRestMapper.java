package com.projarc.trabalhofinal.adapter.in.rest.mapper;

import com.projarc.trabalhofinal.adapter.in.rest.dto.plano.response.ProblemaResponse;
import com.projarc.trabalhofinal.adapter.in.rest.dto.plano.response.ReservaResponse;
import com.projarc.trabalhofinal.adapter.in.rest.dto.plano.response.SituacaoDoPlanoResponse;
import com.projarc.trabalhofinal.adapter.in.rest.dto.plano.response.ValidacaoResponse;
import com.projarc.trabalhofinal.domain.model.plano.PlanoDeVoo;
import com.projarc.trabalhofinal.domain.model.plano.Problema;
import com.projarc.trabalhofinal.domain.model.reserva.Reserva;
import com.projarc.trabalhofinal.domain.model.reserva.ReservaAerovia;
import com.projarc.trabalhofinal.domain.model.reserva.ReservaEstacionamento;

import java.util.List;

public final class CicloDeVidaRestMapper {

    private CicloDeVidaRestMapper() {}

    public static ValidacaoResponse toValidacao(List<Problema> problemas) {
        return ValidacaoResponse.builder()
                .valido(problemas.isEmpty())
                .problemas(problemas.stream()
                        .map(CicloDeVidaRestMapper::fromDomain)
                        .toList())
                .build();
    }

    public static ProblemaResponse fromDomain(Problema problema) {
        return ProblemaResponse.builder()
                .codigo(problema.codigo())
                .mensagem(problema.mensagem())
                .build();
    }

    public static SituacaoDoPlanoResponse toSituacao(PlanoDeVoo plano) {
        return SituacaoDoPlanoResponse.builder()
                .id(plano.getId())
                .status(plano.getStatus().name())
                .reservas(plano.getReservas().stream()
                        .map(CicloDeVidaRestMapper::fromDomain)
                        .toList())
                .build();
    }

    public static ReservaResponse fromDomain(Reserva reserva) {
        return switch (reserva) {
            case ReservaAerovia reservaAerovia -> ReservaResponse.builder()
                    .id(reservaAerovia.getId())
                    .tipo("AEROVIA")
                    .slot(reservaAerovia.getSlot())
                    .aeroviaId(reservaAerovia.getAerovia().getId())
                    .altitude(reservaAerovia.getAltitude())
                    .build();
            case ReservaEstacionamento reservaEstacionamento -> ReservaResponse.builder()
                    .id(reservaEstacionamento.getId())
                    .tipo("ESTACIONAMENTO")
                    .slot(reservaEstacionamento.getSlot())
                    .codigoAeroporto(reservaEstacionamento.getAeroporto().getCodigo())
                    .numeroVaga(reservaEstacionamento.getNumeroVaga())
                    .build();
            default -> throw new IllegalArgumentException("Tipo de reserva desconhecido: " + reserva.getClass().getSimpleName());
        };
    }
}
