package com.projarc.trabalhofinal.domain.model.aeronave;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class JanelaHoraria implements RegraDeOperacao {

    private final LocalTime inicio;
    private final LocalTime fim;

    public JanelaHoraria(LocalTime inicio, LocalTime fim) {
        this.inicio = Objects.requireNonNull(inicio);
        this.fim = Objects.requireNonNull(fim);
        if (!inicio.isBefore(fim)) {
            throw new IllegalArgumentException("A janela deve iniciar antes do fim, no mesmo dia.");
        }
    }

    @Override
    public boolean permite(int altitude, LocalDateTime entrada, LocalDateTime saida) {
        return entrada != null && saida != null
                && saida.isAfter(entrada)
                && entrada.toLocalDate().equals(saida.toLocalDate())
                && !entrada.toLocalTime().isBefore(inicio)
                && saida.toLocalTime().isBefore(fim);
    }
}
