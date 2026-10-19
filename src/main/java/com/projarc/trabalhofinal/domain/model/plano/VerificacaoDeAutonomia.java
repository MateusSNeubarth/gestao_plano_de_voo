package com.projarc.trabalhofinal.domain.model.plano;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VerificacaoDeAutonomia implements VerificacaoDoPlano {

    @Override
    public List<Problema> verificar(PlanoDeVoo plano) {
        if (plano == null) {
            return List.of(new Problema("PLANO_NULO", "O plano de voo não pode ser nulo."));
        }
        if (plano.getAeronave() == null) {
            return List.of();
        }
        Integer autonomia = plano.getAeronave().getAutonomia();
        if (autonomia == null || autonomia <= 0) {
            return List.of(new Problema("AUTONOMIA_INVALIDA", "A autonomia da aeronave deve ser positiva."));
        }
        List<Problema> problemas = new ArrayList<>();
        if (plano.getPercurso() == null || plano.getPercurso().getTrechos() == null) {
            return problemas;
        }
        double limite = plano.getAeronave().distanciaMaximaSemReabastecimento();
        long distanciaAcumulada = 0;
        var trechos = plano.getPercurso().getTrechos();
        for (int i = 0; i < trechos.size(); i++) {
            var trecho = trechos.get(i);
            if (trecho == null || trecho.getAerovia() == null
                    || trecho.getAerovia().getDistanciaKm() == null || trecho.getAerovia().getDistanciaKm() <= 0) {
                return problemas;
            }
            distanciaAcumulada += trecho.getAerovia().getDistanciaKm();
            if (distanciaAcumulada > limite) {
                problemas.add(new Problema("AUTONOMIA_INSUFICIENTE", "O trecho " + (i + 1)
                        + " acumula " + distanciaAcumulada + " km sem reabastecimento, acima do limite de " + limite + " km."));
            }
            if (trecho.getAcaoNaChegada() == AcaoNaChegada.POUSO) {
                distanciaAcumulada = 0;
            }
        }
        return problemas;
    }
}
