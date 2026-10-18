package com.projarc.trabalhofinal.domain.model.plano;

import com.projarc.trabalhofinal.domain.exception.TransicaoInvalidaException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PlanoDeVooTest {

    private static final LocalDateTime PARTIDA = LocalDateTime.of(2026, 11, 10, 8, 0);

    private PlanoDeVoo plano(Status status) {
        return PlanoDeVoo.builder()
                .status(status)
                .dataHoraPartida(PARTIDA)
                .build();
    }

    @Test
    void planoNasceEmEdicaoComPercursoVazio() {
        var plano = PlanoDeVoo.builder().dataHoraPartida(PARTIDA).build();

        assertThat(plano.getStatus()).isEqualTo(Status.EDICAO);
        assertThat(plano.getPercurso().getTrechos()).isEmpty();
        assertThat(plano.getReservas()).isEmpty();
    }

    @Test
    void aprovaPlanoEmEdicao() {
        var plano = plano(Status.EDICAO);

        plano.aprovar();

        assertThat(plano.getStatus()).isEqualTo(Status.APROVADO);
    }

    @ParameterizedTest
    @EnumSource(value = Status.class, names = {"APROVADO", "CANCELADO", "ABANDONADO"})
    void recusaAprovarPlanoForaDeEdicao(Status status) {
        var plano = plano(status);

        assertThatThrownBy(plano::aprovar).isInstanceOf(TransicaoInvalidaException.class);
        assertThat(plano.getStatus()).isEqualTo(status);
    }

    @Test
    void abandonaPlanoEmEdicao() {
        var plano = plano(Status.EDICAO);

        plano.abandonar();

        assertThat(plano.getStatus()).isEqualTo(Status.ABANDONADO);
    }

    @ParameterizedTest
    @EnumSource(value = Status.class, names = {"APROVADO", "CANCELADO", "ABANDONADO"})
    void recusaAbandonarPlanoForaDeEdicao(Status status) {
        var plano = plano(status);

        assertThatThrownBy(plano::abandonar).isInstanceOf(TransicaoInvalidaException.class);
        assertThat(plano.getStatus()).isEqualTo(status);
    }

    @Test
    void cancelaPlanoAprovadoComPartidaFutura() {
        var plano = plano(Status.APROVADO);

        plano.cancelar(PARTIDA.minusMinutes(1));

        assertThat(plano.getStatus()).isEqualTo(Status.CANCELADO);
    }

    @ParameterizedTest
    @EnumSource(value = Status.class, names = {"EDICAO", "CANCELADO", "ABANDONADO"})
    void recusaCancelarPlanoNaoAprovado(Status status) {
        var plano = plano(status);

        assertThatThrownBy(() -> plano.cancelar(PARTIDA.minusDays(1)))
                .isInstanceOf(TransicaoInvalidaException.class);
        assertThat(plano.getStatus()).isEqualTo(status);
    }

    @Test
    void recusaCancelarPlanoNoHorarioDaPartida() {
        var plano = plano(Status.APROVADO);

        assertThatThrownBy(() -> plano.cancelar(PARTIDA)).isInstanceOf(TransicaoInvalidaException.class);
        assertThat(plano.getStatus()).isEqualTo(Status.APROVADO);
    }

    @Test
    void recusaCancelarPlanoComPartidaJaOcorrida() {
        var plano = plano(Status.APROVADO);

        assertThatThrownBy(() -> plano.cancelar(PARTIDA.plusHours(2)))
                .isInstanceOf(TransicaoInvalidaException.class);
        assertThat(plano.getStatus()).isEqualTo(Status.APROVADO);
    }
}
