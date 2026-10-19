package com.projarc.trabalhofinal.domain.model.plano;

import com.projarc.trabalhofinal.domain.model.aeronave.CatalogoDeRegras;
import com.projarc.trabalhofinal.domain.model.aeronave.RegraDeOperacao;
import com.projarc.trabalhofinal.domain.model.malha.Altitudes;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Component
public class VerificacaoDeOperacao implements VerificacaoDoPlano {

    private static final Locale PT_BR = Locale.of("pt", "BR");

    private final CatalogoDeRegras catalogo;

    public VerificacaoDeOperacao(CatalogoDeRegras catalogo) {
        this.catalogo = Objects.requireNonNull(catalogo);
    }

    @Override
    public List<Problema> verificar(PlanoDeVoo plano) {
        if (plano == null) {
            return List.of(new Problema("PLANO_NULO", "O plano de voo não pode ser nulo."));
        }
        var aeronave = plano.getAeronave();
        if (aeronave == null || aeronave.getVelocidadeDeCruzeiro() == null || aeronave.getVelocidadeDeCruzeiro() <= 0) {
            return List.of();
        }
        List<RegraDeOperacao> regras;
        try {
            regras = catalogo.regrasPara(aeronave);
        } catch (IllegalArgumentException e) {
            return List.of(new Problema("CATEGORIA_SEM_REGRAS", e.getMessage()));
        }
        List<Problema> problemas = new ArrayList<>();
        if (plano.getPercurso() == null || plano.getPercurso().getTrechos() == null) {
            return problemas;
        }
        var trechos = plano.getPercurso().getTrechos();
        for (int i = 0; i < trechos.size(); i++) {
            var trecho = trechos.get(i);
            String referencia = "Trecho " + (i + 1) + ": ";
            if (trecho == null) {
                continue;
            }
            Integer altitude = trecho.getAltitude();
            if (!Altitudes.valida(altitude)) {
                problemas.add(new Problema("ALTITUDE_INVALIDA", referencia + String.format(PT_BR,
                        "a altitude deve estar entre %,d e %,d pés, em múltiplos de %,d.",
                        Altitudes.MINIMA, Altitudes.MAXIMA, Altitudes.INTERVALO)));
                continue;
            }
            if (trecho.getAerovia() == null || trecho.getAerovia().getDistanciaKm() == null
                    || trecho.getAerovia().getDistanciaKm() <= 0 || trecho.getHorarioEntradaNaAerovia() == null) {
                continue;
            }
            var entrada = trecho.getHorarioEntradaNaAerovia();
            var chegada = trecho.chegada(aeronave.getVelocidadeDeCruzeiro());
            boolean permitido = regras.stream().allMatch(regra -> regra.permite(altitude, entrada, chegada));
            if (!permitido) {
                problemas.add(new Problema("OPERACAO_NAO_PERMITIDA", referencia
                        + "a altitude ou o intervalo de voo não atende às regras da categoria."));
            }
        }
        return problemas;
    }
}
