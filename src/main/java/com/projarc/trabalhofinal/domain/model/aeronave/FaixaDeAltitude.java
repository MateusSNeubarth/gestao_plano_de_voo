package com.projarc.trabalhofinal.domain.model.aeronave;

import java.time.LocalDateTime;

public class FaixaDeAltitude implements RegraDeOperacao {

    private Integer minimo;
    private Integer maximo;

    @Override
    public boolean permite(int altitude, LocalDateTime entrada, LocalDateTime saida) {
        throw new UnsupportedOperationException("TODO");
    }
}
