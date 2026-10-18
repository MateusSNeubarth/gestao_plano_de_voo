package com.projarc.trabalhofinal.domain.model.plano;

import com.projarc.trabalhofinal.domain.exception.TransicaoInvalidaException;
import com.projarc.trabalhofinal.domain.model.aeronave.Aeronave;
import com.projarc.trabalhofinal.domain.model.malha.Aeroporto;
import com.projarc.trabalhofinal.domain.model.reserva.Reserva;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class PlanoDeVoo {

    private Integer id;
    @Builder.Default
    private Status status = Status.EDICAO;
    private LocalDateTime dataHoraPartida;
    private Aeronave aeronave;
    private Piloto piloto;
    private Aeroporto origem;
    private Aeroporto destino;
    @Builder.Default
    private Percurso percurso = Percurso.builder().build();
    @Builder.Default
    private List<Reserva> reservas = new ArrayList<>();

    public void aprovar() {
        exigirStatus(Status.EDICAO, "Somente planos em edição podem ser aprovados");
        status = Status.APROVADO;
    }

    public void cancelar(LocalDateTime agora) {
        exigirStatus(Status.APROVADO, "Somente planos aprovados podem ser cancelados");
        if (!dataHoraPartida.isAfter(agora)) {
            throw new TransicaoInvalidaException("A partida do plano já ocorreu");
        }
        status = Status.CANCELADO;
    }

    public void abandonar() {
        exigirStatus(Status.EDICAO, "Somente planos em edição podem ser abandonados");
        status = Status.ABANDONADO;
    }

    private void exigirStatus(Status esperado, String mensagem) {
        if (status != esperado) {
            throw new TransicaoInvalidaException(mensagem);
        }
    }
}
