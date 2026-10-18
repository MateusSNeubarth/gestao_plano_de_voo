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
        throw new UnsupportedOperationException("TODO");
    }

    public Boolean podeOperar(Integer altitude, LocalDateTime entrada, LocalDateTime saida) {
        throw new UnsupportedOperationException("TODO");
    }
}
