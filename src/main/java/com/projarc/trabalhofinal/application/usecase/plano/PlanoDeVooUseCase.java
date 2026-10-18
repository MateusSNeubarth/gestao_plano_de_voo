package com.projarc.trabalhofinal.application.usecase.plano;

import com.projarc.trabalhofinal.application.port.in.plano.PlanoDeVooInPort;
import com.projarc.trabalhofinal.application.port.out.plano.PlanoDeVooPort;
import com.projarc.trabalhofinal.domain.model.plano.PlanoDeVoo;
import com.projarc.trabalhofinal.domain.model.plano.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanoDeVooUseCase implements PlanoDeVooInPort {

    private final PlanoDeVooPort planoDeVooPort;

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
        return planoDeVooPort.consultarPlano(id);
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
}
