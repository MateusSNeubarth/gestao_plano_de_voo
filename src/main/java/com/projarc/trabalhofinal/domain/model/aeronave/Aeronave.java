package com.projarc.trabalhofinal.domain.model.aeronave;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Data
@SuperBuilder
public abstract class Aeronave {

    private String prefixo;
    private Integer velocidadeDeCruzeiro;
    private Integer autonomia;
    private List<RegraDeOperacao> regras;

    public Double distanciaMaximaSemReabastecimento() {
        if (autonomia == null || autonomia <= 0) {
            throw new IllegalArgumentException("A autonomia da aeronave deve ser positiva.");
        }
        return autonomia * 0.85;
    }

    public Boolean podeOperar(Integer altitude, LocalDateTime entrada, LocalDateTime saida) {
        return altitude != null && altitude >= 25000 && altitude <= 35000 && altitude % 1000 == 0
                && entrada != null && saida != null && saida.isAfter(entrada)
                && regras != null && !regras.isEmpty()
                && regras.stream().allMatch(regra -> regra.permite(altitude, entrada, saida));
    }
}
