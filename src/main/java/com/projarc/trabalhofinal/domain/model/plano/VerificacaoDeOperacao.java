package com.projarc.trabalhofinal.domain.model.plano;

import com.projarc.trabalhofinal.domain.model.aeronave.CatalogoDeRegras;
import com.projarc.trabalhofinal.domain.model.aeronave.RegraDeOperacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VerificacaoDeOperacao implements VerificacaoDoPlano {

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
        if (aeronave == null) {
            return List.of(new Problema("AERONAVE_NULA", "A aeronave é obrigatória."));
        }
        if (aeronave.getVelocidadeDeCruzeiro() == null || aeronave.getVelocidadeDeCruzeiro() <= 0) {
            return List.of(new Problema("VELOCIDADE_INVALIDA", "A velocidade de cruzeiro deve ser positiva."));
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
                problemas.add(new Problema("TRECHO_NULO", referencia + "o trecho é obrigatório."));
                continue;
            }
            Integer altitude = trecho.getAltitude();
            if (altitude == null || altitude < 25000 || altitude > 35000 || altitude % 1000 != 0) {
                problemas.add(new Problema("ALTITUDE_INVALIDA", referencia
                        + "a altitude deve estar entre 25.000 e 35.000 pés, em múltiplos de 1.000."));
                continue;
            }
            if (trecho.getAerovia() == null || trecho.getAerovia().getDistanciaKm() == null
                    || trecho.getAerovia().getDistanciaKm() <= 0 || trecho.getHorarioEntradaNaAerovia() == null) {
                problemas.add(new Problema("TRECHO_INVALIDO", referencia + "faltam dados válidos para calcular o intervalo."));
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
