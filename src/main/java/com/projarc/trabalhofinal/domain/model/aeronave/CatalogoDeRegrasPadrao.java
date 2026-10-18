package com.projarc.trabalhofinal.domain.model.aeronave;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CatalogoDeRegrasPadrao implements CatalogoDeRegras {

    private final Map<Class<? extends Aeronave>, List<RegraDeOperacao>> regrasPorCategoria;

    public CatalogoDeRegrasPadrao() {
        this(Map.of(
                AeronaveParticular.class, List.of(new FaixaDeAltitude(25000, 27000)),
                AeronaveComercialDePassageiro.class, List.of(new FaixaDeAltitude(28000, 35000)),
                AeronaveComercialDeCarga.class, List.of(new JanelaHoraria(LocalTime.MIDNIGHT, LocalTime.of(6, 0)))
        ));
    }

    public CatalogoDeRegrasPadrao(Map<Class<? extends Aeronave>, List<RegraDeOperacao>> regrasPorCategoria) {
        this.regrasPorCategoria = regrasPorCategoria.entrySet().stream()
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, e -> List.copyOf(e.getValue())));
    }

    @Override
    public List<RegraDeOperacao> regrasPara(Aeronave aeronave) {
        if (aeronave == null || !regrasPorCategoria.containsKey(aeronave.getClass())) {
            throw new IllegalArgumentException("Categoria de aeronave sem regras cadastradas.");
        }
        return regrasPorCategoria.get(aeronave.getClass());
    }
}
