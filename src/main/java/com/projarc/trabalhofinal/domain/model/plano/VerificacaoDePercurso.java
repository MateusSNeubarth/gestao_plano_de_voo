package com.projarc.trabalhofinal.domain.model.plano;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VerificacaoDePercurso implements VerificacaoDoPlano {

    @Override
    public List<Problema> verificar(PlanoDeVoo plano) {
        List<Problema> problemas = new ArrayList<>();

        if (plano == null) {
            problemas.add(new Problema(
                    "PLANO_NULO",
                    "O plano de voo não pode ser nulo."
            ));
            return problemas;
        }

        if (plano.getPercurso() == null) {
            problemas.add(new Problema(
                    "PERCURSO_NULO",
                    "O percurso do plano não pode ser nulo."
            ));
            return problemas;
        }

        try {
            plano.getPercurso().validarContinuidade(
                    plano.getOrigem(),
                    plano.getDestino()
            );
        } catch (IllegalArgumentException e) {
            problemas.add(new Problema(
                    "PERCURSO_INVALIDO",
                    e.getMessage()
            ));
        }

        return problemas;
    }
}