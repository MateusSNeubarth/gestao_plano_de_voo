package com.projarc.trabalhofinal.domain.model.malha;

import java.util.List;
import java.util.stream.IntStream;

public final class Altitudes {

    public static final int MINIMA = 25000;
    public static final int MAXIMA = 35000;
    public static final int INTERVALO = 1000;

    private Altitudes() {}

    public static boolean valida(Integer altitude) {
        return altitude != null && altitude >= MINIMA && altitude <= MAXIMA && altitude % INTERVALO == 0;
    }

    public static List<Integer> todas() {
        return IntStream.iterate(MINIMA, altitude -> altitude <= MAXIMA, altitude -> altitude + INTERVALO)
                .boxed()
                .toList();
    }
}
