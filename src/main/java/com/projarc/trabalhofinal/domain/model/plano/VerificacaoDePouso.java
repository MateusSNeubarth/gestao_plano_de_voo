
package com.projarc.trabalhofinal.domain.model.plano;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VerificacaoDePouso implements VerificacaoDoPlano {

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

        if (plano.getPercurso() == null
                || plano.getPercurso().getTrechos() == null
                || plano.getPercurso().getTrechos().isEmpty()) {
            return problemas;
        }

        List<Trecho> trechos = plano.getPercurso().getTrechos();

        for (int i = 0; i < trechos.size(); i++) {
            Trecho trecho = trechos.get(i);

            if (trecho == null) {
                problemas.add(new Problema(
                        "TRECHO_NULO",
                        "O trecho " + (i + 1) + " não pode ser nulo."
                ));
                continue;
            }

            boolean ultimoTrecho = i == trechos.size() - 1;

            if (ultimoTrecho
                    && trecho.getAcaoNaChegada() != AcaoNaChegada.POUSO) {
                problemas.add(new Problema(
                        "DESTINO_FINAL_SEM_POUSO",
                        "O último trecho deve terminar com pouso."
                ));
            }

            if (trecho.getAcaoNaChegada() == AcaoNaChegada.POUSO) {

                if (trecho.getNumeroVagaPretendida() == null
                        || trecho.getNumeroVagaPretendida() <= 0) {
                    problemas.add(new Problema(
                            "VAGA_INVALIDA",
                            "O pouso no trecho " + (i + 1)
                                    + " deve informar um número de vaga positivo."
                    ));
                }

                if (trecho.getTempoPermanenciaMinutos() == null
                        || trecho.getTempoPermanenciaMinutos() <= 0) {
                    problemas.add(new Problema(
                            "PERMANENCIA_INVALIDA",
                            "O pouso no trecho " + (i + 1)
                                    + " deve informar um tempo de permanência positivo."
                    ));
                }
            }
        }

        return problemas;
    }
}