package com.projarc.trabalhofinal.domain.model.aeronave;

import java.time.LocalDateTime;

public class FaixaDeAltitude implements RegraDeOperacao {

    private final int minimo;
    private final int maximo;

    public FaixaDeAltitude(int minimo, int maximo) {
        if (minimo > maximo) {
            throw new IllegalArgumentException("A altitude mínima não pode superar a máxima.");
        }
        this.minimo = minimo;
        this.maximo = maximo;
    }

    @Override
    public boolean permite(int altitude, LocalDateTime entrada, LocalDateTime saida) {
        return altitude >= minimo && altitude <= maximo;
    }
}
