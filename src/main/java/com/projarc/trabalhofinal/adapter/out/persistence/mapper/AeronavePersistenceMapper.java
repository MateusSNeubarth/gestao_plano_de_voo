package com.projarc.trabalhofinal.adapter.out.persistence.mapper;

import com.projarc.trabalhofinal.adapter.out.persistence.entity.aeronave.AeronaveComercialDeCargaEntity;
import com.projarc.trabalhofinal.adapter.out.persistence.entity.aeronave.AeronaveComercialDePassageiroEntity;
import com.projarc.trabalhofinal.adapter.out.persistence.entity.aeronave.AeronaveEntity;
import com.projarc.trabalhofinal.adapter.out.persistence.entity.aeronave.AeronaveParticularEntity;
import com.projarc.trabalhofinal.domain.model.aeronave.Aeronave;
import com.projarc.trabalhofinal.domain.model.aeronave.AeronaveComercialDeCarga;
import com.projarc.trabalhofinal.domain.model.aeronave.AeronaveComercialDePassageiro;
import com.projarc.trabalhofinal.domain.model.aeronave.AeronaveParticular;

public final class AeronavePersistenceMapper {
    private AeronavePersistenceMapper() {}

    public static Aeronave toDomain(AeronaveEntity entity) {
        if (entity instanceof AeronaveParticularEntity e) {
            return AeronaveParticular.builder()
                    .prefixo(e.getPrefixo())
                    .velocidadeDeCruzeiro(e.getVelocidadeDeCruzeiro())
                    .autonomia(e.getAutonomia())
                    .empresaResponsavelPelaManutencao(e.getEmpresaResponsavelPelaManutencao())
                    .build();
        }
        if (entity instanceof AeronaveComercialDePassageiroEntity e) {
            return AeronaveComercialDePassageiro.builder()
                    .prefixo(e.getPrefixo())
                    .velocidadeDeCruzeiro(e.getVelocidadeDeCruzeiro())
                    .autonomia(e.getAutonomia())
                    .companhiaAerea(e.getCompanhiaAerea())
                    .capacidadeMaxPassageiro(e.getCapacidadeMaxPassageiro())
                    .build();
        }
        if (entity instanceof AeronaveComercialDeCargaEntity e) {
            return AeronaveComercialDeCarga.builder()
                    .prefixo(e.getPrefixo())
                    .velocidadeDeCruzeiro(e.getVelocidadeDeCruzeiro())
                    .autonomia(e.getAutonomia())
                    .companhiaAerea(e.getCompanhiaAerea())
                    .capacidadeMaxCargaTn(e.getCapacidadeMaxCargaTn())
                    .build();
        }
        throw new IllegalStateException("Tipo de entity desconhecido: " + entity.getClass().getSimpleName());
    }

    public static AeronaveEntity toEntity(Aeronave aeronave) {
        if (aeronave instanceof AeronaveParticular a) {
            return AeronaveParticularEntity.builder()
                    .prefixo(a.getPrefixo())
                    .velocidadeDeCruzeiro(a.getVelocidadeDeCruzeiro())
                    .autonomia(a.getAutonomia())
                    .empresaResponsavelPelaManutencao(a.getEmpresaResponsavelPelaManutencao())
                    .build();
        }
        if (aeronave instanceof AeronaveComercialDePassageiro a) {
            return AeronaveComercialDePassageiroEntity.builder()
                    .prefixo(a.getPrefixo())
                    .velocidadeDeCruzeiro(a.getVelocidadeDeCruzeiro())
                    .autonomia(a.getAutonomia())
                    .companhiaAerea(a.getCompanhiaAerea())
                    .capacidadeMaxPassageiro(a.getCapacidadeMaxPassageiro())
                    .build();
        }
        if (aeronave instanceof AeronaveComercialDeCarga a) {
            return AeronaveComercialDeCargaEntity.builder()
                    .prefixo(a.getPrefixo())
                    .velocidadeDeCruzeiro(a.getVelocidadeDeCruzeiro())
                    .autonomia(a.getAutonomia())
                    .companhiaAerea(a.getCompanhiaAerea())
                    .capacidadeMaxCargaTn(a.getCapacidadeMaxCargaTn())
                    .build();
        }
        throw new IllegalArgumentException("Tipo de aeronave não suportado");
    }
}