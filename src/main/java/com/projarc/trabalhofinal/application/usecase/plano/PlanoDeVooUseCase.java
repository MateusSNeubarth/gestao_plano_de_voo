package com.projarc.trabalhofinal.application.usecase.plano;

import com.projarc.trabalhofinal.application.port.in.plano.PlanoDeVooInPort;
import com.projarc.trabalhofinal.application.port.out.malha.AeroportoPort;
import com.projarc.trabalhofinal.application.port.out.malha.AeroviaPort;
import com.projarc.trabalhofinal.application.port.out.plano.PlanoDeVooPort;
import com.projarc.trabalhofinal.domain.exception.PlanoNaoEncontradoException;
import com.projarc.trabalhofinal.domain.exception.TransicaoInvalidaException;
import com.projarc.trabalhofinal.domain.model.plano.Percurso;
import com.projarc.trabalhofinal.domain.model.plano.PlanoDeVoo;
import com.projarc.trabalhofinal.domain.model.plano.Status;
import com.projarc.trabalhofinal.domain.model.plano.Trecho;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanoDeVooUseCase implements PlanoDeVooInPort {

    private final PlanoDeVooPort planoDeVooPort;
    private final AeroviaPort aeroviaPort;
    private final AeroportoPort aeroportoPort;

    @Override
    public PlanoDeVoo criarPlano(PlanoDeVoo plano) {
        if (plano == null) {
            throw new IllegalArgumentException(
                    "O plano de voo não pode ser nulo."
            );
        }

        if (plano.getStatus() != Status.EDICAO) {
            throw new IllegalArgumentException(
                    "O plano de voo deve estar em status de edição."
            );
        }

        if (plano.getDataHoraPartida() == null) {
            throw new IllegalArgumentException(
                    "A data e hora de partida são obrigatórias."
            );
        }

        // Validação do piloto
        if (plano.getPiloto() == null) {
            throw new IllegalArgumentException(
                    "O piloto não pode ser nulo."
            );
        }

        if (plano.getPiloto().getId() == null) {
            throw new IllegalArgumentException(
                    "O ID do piloto não pode ser nulo."
            );
        }

        // Validação da aeronave
        if (plano.getAeronave() == null) {
            throw new IllegalArgumentException(
                    "A aeronave não pode ser nula."
            );
        }

        if (plano.getAeronave().getPrefixo() == null
                || plano.getAeronave().getPrefixo().isBlank()) {
            throw new IllegalArgumentException(
                    "O prefixo da aeronave não pode ser nulo ou vazio."
            );
        }

        // Validação da origem
        if (plano.getOrigem() == null) {
            throw new IllegalArgumentException(
                    "O aeroporto de origem não pode ser nulo."
            );
        }

        if (plano.getOrigem().getCodigo() == null
                || plano.getOrigem().getCodigo().isBlank()) {
            throw new IllegalArgumentException(
                    "O código do aeroporto de origem não pode ser nulo ou vazio."
            );
        }

        // Validação do destino
        if (plano.getDestino() == null) {
            throw new IllegalArgumentException(
                    "O aeroporto de destino não pode ser nulo."
            );
        }

        if (plano.getDestino().getCodigo() == null
                || plano.getDestino().getCodigo().isBlank()) {
            throw new IllegalArgumentException(
                    "O código do aeroporto de destino não pode ser nulo ou vazio."
            );
        }

        return planoDeVooPort.criarPlano(plano);
    }

    @Override
    public PlanoDeVoo consultarPlano(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "O ID do plano de voo deve ser maior que zero."
            );
        }
        var plano = planoDeVooPort.getPlanoDeVoo(id);
        if (plano == null) {
            throw new PlanoNaoEncontradoException(id);
        }
        return plano;
    }

    @Override
    public List<PlanoDeVoo> listarPlanosPorStatus(Status status) {
        if (status == null) {
            throw new IllegalArgumentException(
                    "O Status não pode ser nulo."
            );
        }
        return planoDeVooPort.listarPlanosPorStatus(status);
    }

    @Override
    @Transactional
    public PlanoDeVoo substituirPercurso(Integer id, List<Trecho> trechos) {
        var plano = getPlanoEmEdicao(id);
        if (trechos == null) {
            throw new IllegalArgumentException("A lista de trechos não pode ser nula.");
        }
        return gravarPercurso(plano, trechos);
    }

    @Override
    @Transactional
    public PlanoDeVoo alterarTrecho(Integer id, int ordem, Trecho trecho) {
        var plano = getPlanoEmEdicao(id);
        var trechos = new ArrayList<>(plano.getPercurso().getTrechos());
        if (ordem < 1 || ordem > trechos.size()) {
            throw new IllegalArgumentException("O plano não possui o trecho " + ordem + ".");
        }
        trechos.set(ordem - 1, trecho);
        return gravarPercurso(plano, trechos);
    }

    private PlanoDeVoo gravarPercurso(PlanoDeVoo plano, List<Trecho> trechos) {
        var percurso = Percurso.builder().build();
        percurso.substituirTrechos(trechos.stream().map(this::resolverTrecho).toList());
        plano.substituirPercurso(percurso);
        return planoDeVooPort.updatePercurso(plano.getId(), plano.getPercurso());
    }

    private Trecho resolverTrecho(Trecho trecho) {
        if (trecho == null || trecho.getAerovia() == null || trecho.getAerovia().getId() == null
                || trecho.getAltitude() == null || trecho.getHorarioEntradaNaAerovia() == null
                || trecho.getAcaoNaChegada() == null) {
            throw new IllegalArgumentException(
                    "Todo trecho deve informar aerovia, altitude, horário de entrada e ação na chegada."
            );
        }

        var aerovia = aeroviaPort.getAerovia(trecho.getAerovia().getId());
        if (aerovia == null) {
            throw new IllegalArgumentException("Aerovia não encontrada: " + trecho.getAerovia().getId());
        }
        trecho.setAerovia(aerovia);

        if (trecho.getNumeroVagaPretendida() != null) {
            var destino = aeroportoPort.getAeroporto(aerovia.getCodDestino());
            if (trecho.getNumeroVagaPretendida() < 1 || trecho.getNumeroVagaPretendida() > destino.getQtdVagas()) {
                throw new IllegalArgumentException(
                        "A vaga " + trecho.getNumeroVagaPretendida()
                                + " não pertence ao aeroporto " + destino.getCodigo() + "."
                );
            }
        }
        return trecho;
    }

    private PlanoDeVoo getPlanoEmEdicao(Integer id) {
        var plano = planoDeVooPort.getPlanoDeVoo(id);
        if (plano == null) {
            throw new PlanoNaoEncontradoException(id);
        }
        if (plano.getStatus() != Status.EDICAO) {
            throw new TransicaoInvalidaException("Somente planos em edição podem ter o percurso alterado");
        }
        return plano;
    }
}
