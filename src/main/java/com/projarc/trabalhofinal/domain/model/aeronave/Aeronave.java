package com.projarc.trabalhofinal.domain.model.aeronave;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public abstract class Aeronave {

    private String prefixo;
    private Integer velocidadeDeCruzeiro;
    private Integer autonomia;

    public Double distanciaMaximaSemReabastecimento() {
        if (autonomia == null || autonomia <= 0) {
            throw new IllegalArgumentException("A autonomia da aeronave deve ser positiva.");
        }
        return autonomia * 0.85;
    }
}
