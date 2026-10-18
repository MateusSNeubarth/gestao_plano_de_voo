
package com.projarc.trabalhofinal.domain.model.plano;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VerificacaoDeHorario implements VerificacaoDoPlano {

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

        if (plano.getDataHoraPartida() == null) {
            problemas.add(new Problema(
                    "PARTIDA_NULA",
                    "A data e hora de partida são obrigatórias."
            ));
            return problemas;
        }

        if (plano.getAeronave() == null
                || plano.getAeronave().getVelocidadeDeCruzeiro() == null
                || plano.getAeronave().getVelocidadeDeCruzeiro() <= 0) {
            problemas.add(new Problema(
                    "VELOCIDADE_INVALIDA",
                    "A velocidade de cruzeiro da aeronave deve ser positiva."
            ));
            return problemas;
        }

        if (plano.getPercurso() == null
                || plano.getPercurso().getTrechos() == null
                || plano.getPercurso().getTrechos().isEmpty()) {
            problemas.add(new Problema(
                    "PERCURSO_VAZIO",
                    "O percurso deve possuir pelo menos um trecho."
            ));
            return problemas;
        }

        List<Trecho> trechos = plano.getPercurso().getTrechos();
        int velocidade = plano.getAeronave().getVelocidadeDeCruzeiro();

        LocalDateTime horarioEsperado = plano.getDataHoraPartida();

        for (int i = 0; i < trechos.size(); i++) {
            Trecho trecho = trechos.get(i);

            if (trecho == null
                    || trecho.getAerovia() == null
                    || trecho.getHorarioEntradaNaAerovia() == null) {
                problemas.add(new Problema(
                        "TRECHO_INVALIDO",
                        "O trecho " + (i + 1)
                                + " possui dados obrigatórios ausentes."
                ));
                return problemas;
            }

            if (!trecho.getHorarioEntradaNaAerovia()
                    .equals(horarioEsperado)) {
                problemas.add(new Problema(
                        "HORARIO_ENTRADA_INVALIDO",
                        "O horário de entrada do trecho " + (i + 1)
                                + " deve ser " + horarioEsperado + "."
                ));
            }

            if (trecho.getAerovia().getDistanciaKm() == null
                    || trecho.getAerovia().getDistanciaKm() <= 0) {
                problemas.add(new Problema(
                        "DISTANCIA_INVALIDA",
                        "A distância da aerovia do trecho "
                                + (i + 1) + " deve ser positiva."
                ));
                return problemas;
            }

            LocalDateTime chegada = trecho.chegada(velocidade);

            if (i == trechos.size() - 1) {
                if (!chegada.toLocalDate()
                        .equals(plano.getDataHoraPartida().toLocalDate())) {
                    problemas.add(new Problema(
                            "CHEGADA_OUTRO_DIA",
                            "O voo deve terminar no mesmo dia da partida."
                    ));
                }

                continue;
            }

            if (trecho.getAcaoNaChegada() == null) {
                problemas.add(new Problema(
                        "ACAO_NA_CHEGADA_NULA",
                        "A ação na chegada do trecho " + (i + 1)
                                + " é obrigatória."
                ));
                continue;
            }

            if (trecho.getAcaoNaChegada() == AcaoNaChegada.SOBREVOO) {
                horarioEsperado = chegada;
            } else if (trecho.getAcaoNaChegada() == AcaoNaChegada.POUSO) {
                Optional<LocalDateTime> fimPermanencia =
                        trecho.fimDaPermanencia(velocidade);

                if (trecho.getTempoPermanenciaMinutos() == null
                        || trecho.getTempoPermanenciaMinutos() <= 0) {
                    problemas.add(new Problema(
                            "PERMANENCIA_INVALIDA",
                            "O pouso no trecho " + (i + 1)
                                    + " deve possuir tempo de permanência positivo."
                    ));
                    continue;
                }

                horarioEsperado = fimPermanencia.orElse(chegada);
            }
        }

        return problemas;
    }
}

/**
 * Verifica se os horários dos trechos do plano de voo são válidos.
 *
 * Responsabilidades:
 * - Garantir que o primeiro trecho comece no horário de partida.
 * - Verificar a sequência dos horários entre os trechos.
 * - Considerar o tempo de voo e a permanência após os pousos.
 * - Garantir que a chegada ao destino final ocorra no mesmo dia
 *   da partida.
 */
