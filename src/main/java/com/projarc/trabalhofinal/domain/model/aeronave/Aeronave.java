package com.projarc.trabalhofinal.domain.model.aeronave;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public abstract class Aeronave {

    private String prefixo;
    private Integer velocidadeDeCruzeiro;
    private Integer autonomia;
    private List<RegraDeOperacao> regras;

    public Aeronave(String prefixo, Integer velocidadeDeCruzeiro, Integer autonomia) {
        this.prefixo = prefixo;
        this.velocidadeDeCruzeiro = velocidadeDeCruzeiro;
        this.autonomia = autonomia;
    }

    public Double distanciaMaximaSemReabastecimento() {
        throw new UnsupportedOperationException("TODO");
    }

    public Boolean podeOperar(Integer altitude, LocalDateTime entrada, LocalDateTime saida) {
        throw new UnsupportedOperationException("TODO");
    }
}
