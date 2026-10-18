package com.projarc.trabalhofinal.domain.model.plano;

import com.projarc.trabalhofinal.domain.model.malha.Aerovia;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class TrechoTest {

    private static final LocalDateTime ENTRADA = LocalDateTime.of(2026, 11, 10, 8, 0);

    private Trecho.TrechoBuilder trecho(int distanciaKm) {
        return Trecho.builder()
                .aerovia(Aerovia.builder().id("R1").codOrigem("POA").codDestino("FLN").distanciaKm(distanciaKm).build())
                .altitude(26000)
                .horarioEntradaNaAerovia(ENTRADA);
    }

    @Test
    void calculaChegadaPelaDistanciaEVelocidade() {
        var trecho = trecho(400).acaoNaChegada(AcaoNaChegada.SOBREVOO).build();

        assertThat(trecho.chegada(800)).isEqualTo(ENTRADA.plusMinutes(30));
    }

    @Test
    void arredondaChegadaParaOMinutoSeguinte() {
        var trecho = trecho(363).acaoNaChegada(AcaoNaChegada.SOBREVOO).build();

        assertThat(trecho.chegada(800)).isEqualTo(ENTRADA.plusMinutes(28));
    }

    @Test
    void chegadaPodePassarDaMeiaNoite() {
        var trecho = trecho(900).acaoNaChegada(AcaoNaChegada.SOBREVOO)
                .horarioEntradaNaAerovia(LocalDateTime.of(2026, 11, 10, 23, 30))
                .build();

        assertThat(trecho.chegada(600)).isEqualTo(LocalDateTime.of(2026, 11, 11, 1, 0));
    }

    @Test
    void fimDaPermanenciaSomaPermanenciaAChegadaNoPouso() {
        var trecho = trecho(400).acaoNaChegada(AcaoNaChegada.POUSO)
                .numeroVagaPretendida(3)
                .tempoPermanenciaMinutos(45)
                .build();

        assertThat(trecho.fimDaPermanencia(800)).contains(ENTRADA.plusMinutes(75));
    }

    @Test
    void semFimDaPermanenciaNoSobrevoo() {
        var trecho = trecho(400).acaoNaChegada(AcaoNaChegada.SOBREVOO).build();

        assertThat(trecho.fimDaPermanencia(800)).isEmpty();
    }

    @Test
    void semFimDaPermanenciaNoPousoSemPermanencia() {
        var trecho = trecho(400).acaoNaChegada(AcaoNaChegada.POUSO).numeroVagaPretendida(3).build();

        assertThat(trecho.fimDaPermanencia(800)).isEmpty();
    }
}
