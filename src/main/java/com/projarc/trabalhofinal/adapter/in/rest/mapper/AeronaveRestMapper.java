package com.projarc.trabalhofinal.adapter.in.rest.mapper;

import com.projarc.trabalhofinal.adapter.in.rest.dto.AeronaveRequest;
import com.projarc.trabalhofinal.adapter.in.rest.dto.AeronaveResponse;
import com.projarc.trabalhofinal.domain.model.aeronave.Aeronave;
import com.projarc.trabalhofinal.domain.model.aeronave.AeronaveComercialDeCarga;
import com.projarc.trabalhofinal.domain.model.aeronave.AeronaveComercialDePassageiro;
import com.projarc.trabalhofinal.domain.model.aeronave.AeronaveParticular;

public final class AeronaveRestMapper {
    private AeronaveRestMapper() {}

    public static AeronaveResponse toResponse(Aeronave aeronave) {
        if (aeronave instanceof AeronaveParticular a) {
            return new AeronaveResponse(a.getPrefixo(), "PARTICULAR",
                    a.getVelocidadeDeCruzeiro(), a.getAutonomia(),
                    a.getEmpresaResponsavelPelaManutencao(), null, null, null);
        }
        if (aeronave instanceof AeronaveComercialDePassageiro a) {
            return new AeronaveResponse(a.getPrefixo(), "COMERCIAL_DE_PASSAGEIRO",
                    a.getVelocidadeDeCruzeiro(), a.getAutonomia(),
                    null, a.getCompanhiaAerea(), a.getCapacidadeMaxPassageiro(), null);
        }
        if (aeronave instanceof AeronaveComercialDeCarga a) {
            return new AeronaveResponse(a.getPrefixo(), "COMERCIAL_DE_CARGA",
                    a.getVelocidadeDeCruzeiro(), a.getAutonomia(),
                    null, a.getCompanhiaAerea(), null, a.getCapacidadeMaxCargaTn());
        }
        throw new IllegalArgumentException("Tipo de aeronave não suportado");
    }

    public static Aeronave toDomain(AeronaveRequest request) {
        return switch (request.categoria()) {
            case "PARTICULAR" -> AeronaveParticular.builder()
                    .prefixo(request.prefixo())
                    .velocidadeDeCruzeiro(request.velocidadeDeCruzeiro())
                    .autonomia(request.autonomia())
                    .empresaResponsavelPelaManutencao(request.empresaResponsavelPelaManutencao())
                    .build();
            case "COMERCIAL_DE_PASSAGEIRO" -> AeronaveComercialDePassageiro.builder()
                    .prefixo(request.prefixo())
                    .velocidadeDeCruzeiro(request.velocidadeDeCruzeiro())
                    .autonomia(request.autonomia())
                    .companhiaAerea(request.companhiaAerea())
                    .capacidadeMaxPassageiro(request.capacidadeMaxPassageiro())
                    .build();
            case "COMERCIAL_DE_CARGA" -> AeronaveComercialDeCarga.builder()
                    .prefixo(request.prefixo())
                    .velocidadeDeCruzeiro(request.velocidadeDeCruzeiro())
                    .autonomia(request.autonomia())
                    .companhiaAerea(request.companhiaAerea())
                    .capacidadeMaxCargaTn(request.capacidadeMaxCargaTn())
                    .build();
            default -> throw new IllegalArgumentException("Categoria desconhecida: " + request.categoria());
        };
    }
}