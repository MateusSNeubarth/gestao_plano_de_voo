package com.projarc.trabalhofinal.domain.model.plano;

import com.projarc.trabalhofinal.config.RegrasDeOperacaoConfig;
import com.projarc.trabalhofinal.application.usecase.plano.ValidadorDePlano;
import com.projarc.trabalhofinal.domain.model.aeronave.*;
import com.projarc.trabalhofinal.domain.model.malha.Aerovia;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class RegrasDeOperacaoTest {

    private final CatalogoDeRegras catalogo = new CatalogoDeRegrasPadrao();
    private final VerificacaoDeOperacao operacao = new VerificacaoDeOperacao(catalogo);
    private final VerificacaoDeAutonomia autonomia = new VerificacaoDeAutonomia();
    private static final LocalDateTime ENTRADA = LocalDateTime.of(2026, 11, 10, 0, 0);

    private Aeronave particular() {
        return AeronaveParticular.builder().autonomia(1000).velocidadeDeCruzeiro(600).build();
    }

    private Aeronave passageiros() {
        return AeronaveComercialDePassageiro.builder().autonomia(1000).velocidadeDeCruzeiro(600).build();
    }

    private Aeronave carga() {
        return AeronaveComercialDeCarga.builder().autonomia(1000).velocidadeDeCruzeiro(600).build();
    }

    private Trecho trecho(int distancia, int altitude, AcaoNaChegada acao) {
        return Trecho.builder().aerovia(Aerovia.builder().distanciaKm(distancia).build())
                .altitude(altitude).horarioEntradaNaAerovia(ENTRADA).acaoNaChegada(acao).build();
    }

    private PlanoDeVoo plano(Aeronave aeronave, Trecho... trechos) {
        return PlanoDeVoo.builder().aeronave(aeronave)
                .percurso(Percurso.builder().trechos(List.of(trechos)).build()).build();
    }

    @Test
    void particularA30000Falha() {
        assertThat(operacao.verificar(plano(particular(), trecho(100, 30000, AcaoNaChegada.POUSO))))
                .extracting(Problema::codigo).containsExactly("OPERACAO_NAO_PERMITIDA");
    }

    @ParameterizedTest
    @ValueSource(ints = {25000, 26000, 27000})
    void aceitaAltitudesDaParticular(int altitude) {
        assertThat(operacao.verificar(plano(particular(), trecho(100, altitude, AcaoNaChegada.POUSO)))).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(ints = {28000, 35000})
    void aceitaLimitesDePassageiros(int altitude) {
        assertThat(operacao.verificar(plano(passageiros(), trecho(100, altitude, AcaoNaChegada.POUSO)))).isEmpty();
    }

    @Test
    void passageirosA27000Falha() {
        assertThat(operacao.verificar(plano(passageiros(), trecho(100, 27000, AcaoNaChegada.POUSO))))
                .extracting(Problema::codigo).containsExactly("OPERACAO_NAO_PERMITIDA");
    }

    @ParameterizedTest
    @ValueSource(ints = {24000, 25500, 36000})
    void altitudeInvalidaParaTodasAsCategorias(int altitude) {
        for (var aeronave : List.of(particular(), passageiros(), carga())) {
            assertThat(operacao.verificar(plano(aeronave, trecho(100, altitude, AcaoNaChegada.POUSO))))
                    .extracting(Problema::codigo).containsExactly("ALTITUDE_INVALIDA");
        }
    }

    @Test
    void cargaTerminandoAs0620Falha() {
        var trecho = trecho(200, 25000, AcaoNaChegada.POUSO);
        trecho.setHorarioEntradaNaAerovia(ENTRADA.plusHours(6));
        assertThat(operacao.verificar(plano(carga(), trecho)))
                .extracting(Problema::codigo).containsExactly("OPERACAO_NAO_PERMITIDA");
    }

    @Test
    void cargaQueComecaAntesDas06MasTerminaDepoisFalha() {
        var trecho = trecho(800, 35000, AcaoNaChegada.POUSO);
        trecho.setHorarioEntradaNaAerovia(ENTRADA.plusHours(5));
        assertThat(operacao.verificar(plano(carga(), trecho)))
                .extracting(Problema::codigo).containsExactly("OPERACAO_NAO_PERMITIDA");
    }

    @Test
    void janelaIncluiMeiaNoiteMasExcluiChegadaAs06() {
        var regra = new JanelaHoraria(LocalTime.MIDNIGHT, LocalTime.of(6, 0));
        assertThat(regra.permite(25000, ENTRADA, ENTRADA.plusHours(6).minusNanos(1))).isTrue();
        assertThat(regra.permite(25000, ENTRADA, ENTRADA.plusHours(6))).isFalse();
        assertThat(regra.permite(25000, ENTRADA, ENTRADA.plusDays(1).plusHours(1))).isFalse();
        assertThat(regra.permite(25000, ENTRADA, ENTRADA)).isFalse();
        assertThat(regra.permite(25000, ENTRADA, ENTRADA.minusMinutes(1))).isFalse();
        assertThat(regra.permite(25000, null, ENTRADA)).isFalse();
    }

    @Test
    void chegadaCalculadaRespeitaArredondamentoDoTrecho() {
        var trecho = trecho(11, 25000, AcaoNaChegada.POUSO);
        trecho.setHorarioEntradaNaAerovia(ENTRADA.plusHours(5).plusMinutes(58));
        assertThat(operacao.verificar(plano(carga(), trecho))).isNotEmpty();
    }

    @Test
    void permanenciaEmSoloNaoIntegraJanelaDeOperacao() {
        var trecho = trecho(100, 35000, AcaoNaChegada.POUSO);
        trecho.setHorarioEntradaNaAerovia(ENTRADA.plusHours(5));
        trecho.setTempoPermanenciaMinutos(180);
        assertThat(operacao.verificar(plano(carga(), trecho))).isEmpty();
    }

    @Test
    void sobrevoosAcumulamDistanciaEPousoIntermediarioReabastece() {
        var primeiro = trecho(450, 26000, AcaoNaChegada.SOBREVOO);
        var segundo = trecho(450, 26000, AcaoNaChegada.SOBREVOO);
        var plano = plano(particular(), primeiro, segundo);
        assertThat(autonomia.verificar(plano)).extracting(Problema::codigo).containsExactly("AUTONOMIA_INSUFICIENTE");
        primeiro.setAcaoNaChegada(AcaoNaChegada.POUSO);
        assertThat(autonomia.verificar(plano)).isEmpty();
    }

    @Test
    void limiteDe85PorCentoEhInclusivo() {
        assertThat(autonomia.verificar(plano(particular(), trecho(400, 26000, AcaoNaChegada.SOBREVOO),
                trecho(450, 26000, AcaoNaChegada.POUSO)))).isEmpty();
        assertThat(autonomia.verificar(plano(particular(), trecho(851, 26000, AcaoNaChegada.POUSO))))
                .extracting(Problema::codigo).containsExactly("AUTONOMIA_INSUFICIENTE");
    }

    @Test
    void dadosIncompletosRetornamProblemas() {
        assertThat(operacao.verificar(null)).extracting(Problema::codigo).containsExactly("PLANO_NULO");
        assertThat(autonomia.verificar(null)).extracting(Problema::codigo).containsExactly("PLANO_NULO");
        var plano = plano(particular(), trecho(100, 25000, AcaoNaChegada.POUSO));
        plano.getAeronave().setAutonomia(null);
        assertThat(autonomia.verificar(plano)).extracting(Problema::codigo).containsExactly("AUTONOMIA_INVALIDA");
        plano.getPercurso().getTrechos().getFirst().setAltitude(null);
        assertThat(operacao.verificar(plano)).extracting(Problema::codigo).containsExactly("ALTITUDE_INVALIDA");
    }

    @Test
    void catalogoPodeReceberOutraComposicaoSemAlterarVerificacao() {
        var personalizado = new CatalogoDeRegrasPadrao(Map.of(AeronaveParticular.class,
                List.of(new FaixaDeAltitude(30000, 32000))));
        assertThat(new VerificacaoDeOperacao(personalizado)
                .verificar(plano(particular(), trecho(100, 31000, AcaoNaChegada.POUSO)))).isEmpty();
        assertThatThrownBy(() -> personalizado.regrasPara(carga())).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> catalogo.regrasPara(particular()).clear()).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void aeronaveAplicaTodasAsRegrasAssociadas() {
        var aeronave = particular();
        aeronave.setRegras(List.of(new FaixaDeAltitude(25000, 27000),
                new JanelaHoraria(LocalTime.MIDNIGHT, LocalTime.of(6, 0))));
        assertThat(aeronave.podeOperar(26000, ENTRADA, ENTRADA.plusHours(1))).isTrue();
        assertThat(aeronave.podeOperar(26000, ENTRADA, ENTRADA.plusHours(7))).isFalse();
        assertThat(aeronave.podeOperar(25500, ENTRADA, ENTRADA.plusHours(1))).isFalse();
        assertThat(aeronave.distanciaMaximaSemReabastecimento()).isEqualTo(850.0);
    }

    @Test
    void configuracaoIntegraVerificacoesSemReservarOuAlterarPlano() {
        try (var context = new AnnotationConfigApplicationContext(RegrasDeOperacaoConfig.class, ValidadorDePlano.class)) {
            var plano = plano(particular(), trecho(900, 30000, AcaoNaChegada.POUSO));
            assertThat(context.getBean(ValidadorDePlano.class).validar(plano))
                    .extracting(Problema::codigo).containsExactlyInAnyOrder("OPERACAO_NAO_PERMITIDA", "AUTONOMIA_INSUFICIENTE");
            assertThat(plano.getStatus()).isEqualTo(Status.EDICAO);
            assertThat(plano.getReservas()).isEmpty();
            assertThat(plano.getAeronave().getRegras()).isNull();
        }
    }
}
