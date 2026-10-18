package com.projarc.trabalhofinal.adapter.out.persistence.mapper;


import com.projarc.trabalhofinal.adapter.out.persistence.entity.AeronaveEntity;
import com.projarc.trabalhofinal.domain.model.aeronave.Aeronave;
import com.projarc.trabalhofinal.domain.model.aeronave.AeronaveComercialDeCarga;
import com.projarc.trabalhofinal.domain.model.aeronave.AeronaveComercialDePassageiro;
import com.projarc.trabalhofinal.domain.model.aeronave.AeronaveParticular;

public final class AeronavePersistenceMapper {
    private AeronavePersistenceMapper() {}

    public static Aeronave toDomain(AeronaveEntity entity) {
        return switch (entity.getCategoria()) {
            case "PARTICULAR" -> new AeronaveParticular(entity.getPrefixo(), entity.getVelocidadeDeCruzeiro(), entity.getAutonomia(), entity.getEmpresaResponsavelPelaManutencao());
            case "COMERCIAL_PASSAGEIROS" -> new AeronaveComercialDePassageiro(entity.getPrefixo(), entity.getVelocidadeDeCruzeiro(), entity.getAutonomia(), entity.getCompanhiaAerea(), entity.getCapacidadeMaxPassageiro());
            case "COMERCIAL_CARGA" -> new AeronaveComercialDeCarga(entity.getPrefixo(), entity.getVelocidadeDeCruzeiro(), entity.getAutonomia(), entity.getCompanhiaAerea(), entity.getCapacidadeMaxCargaTn());
            default -> throw new IllegalStateException("Categoria persistida desconhecida: " + entity.getCategoria());
        };
    }

    public static AeronaveEntity toEntity(Aeronave aeronave) {
        var entity = AeronaveEntity.builder().prefixo(aeronave.getPrefixo()).velocidadeDeCruzeiro(aeronave.getVelocidadeDeCruzeiro())
                .autonomia(aeronave.getAutonomia());

        if (aeronave instanceof AeronaveParticular a) {
            return entity.categoria("PARTICULAR").empresaResponsavelPelaManutencao(a.getEmpresaResponsavelPelaManutencao())
                    .build();
        }
        if (aeronave instanceof AeronaveComercialDePassageiro a) {
            return entity.categoria("COMERCIAL_PASSAGEIROS").companhiaAerea(a.getCompanhiaAerea())
                    .capacidadeMaxPassageiro(a.getCapacidadeMaxPassageiro())
                    .build();
        }
        if (aeronave instanceof AeronaveComercialDeCarga a) {
            return entity.categoria("COMERCIAL_CARGA").companhiaAerea(a.getCompanhiaAerea())
                    .capacidadeMaxCargaTn(a.getCapacidadeMaxCargaTn())
                    .build();
        }
        throw new IllegalArgumentException("Tipo de aeronave não suportado");
    }
}