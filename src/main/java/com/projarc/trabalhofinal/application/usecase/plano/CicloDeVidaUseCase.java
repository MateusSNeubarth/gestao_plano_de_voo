package com.projarc.trabalhofinal.application.usecase.plano;

import com.projarc.trabalhofinal.application.port.in.plano.CicloDeVidaInPort;
import com.projarc.trabalhofinal.application.port.out.plano.PlanoDeVooPort;
import com.projarc.trabalhofinal.domain.exception.ConflitoDeReservaException;
import com.projarc.trabalhofinal.domain.exception.PlanoInvalidoException;
import com.projarc.trabalhofinal.domain.exception.PlanoNaoEncontradoException;
import com.projarc.trabalhofinal.domain.exception.TransicaoInvalidaException;
import com.projarc.trabalhofinal.domain.model.plano.PlanoDeVoo;
import com.projarc.trabalhofinal.domain.model.plano.Problema;
import com.projarc.trabalhofinal.domain.model.plano.Status;
import com.projarc.trabalhofinal.domain.model.reserva.ServicoDeReservas;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CicloDeVidaUseCase implements CicloDeVidaInPort {

    private final PlanoDeVooPort planoDeVooPort;
    private final ValidadorDePlano validadorDePlano;
    private final ServicoDeReservas servicoDeReservas;
    private final Clock clock;

    @Override
    @Transactional(readOnly = true)
    public List<Problema> validar(Integer id) {
        var plano = getPlanoEmEdicao(id, "Somente planos em edição podem ser validados");
        return validadorDePlano.validar(plano);
    }

    @Override
    @Transactional
    public PlanoDeVoo aprovar(Integer id) {
        var plano = getPlanoEmEdicao(id, "Somente planos em edição podem ser aprovados");
        var problemas = validadorDePlano.validar(plano);
        if (!problemas.isEmpty()) {
            throw new PlanoInvalidoException(problemas);
        }
        try {
            servicoDeReservas.reservar(plano);
        } catch (DataIntegrityViolationException e) {
            throw new ConflitoDeReservaException("Um dos recursos do plano foi reservado por outro plano", e);
        }
        plano.aprovar();
        planoDeVooPort.updateStatus(plano.getId(), plano.getStatus());
        return plano;
    }

    @Override
    @Transactional
    public PlanoDeVoo abandonar(Integer id) {
        var plano = getPlano(id);
        plano.abandonar();
        planoDeVooPort.updateStatus(plano.getId(), plano.getStatus());
        return plano;
    }

    @Override
    @Transactional
    public PlanoDeVoo cancelar(Integer id) {
        var plano = getPlano(id);
        plano.cancelar(LocalDateTime.now(clock));
        servicoDeReservas.liberar(plano);
        planoDeVooPort.updateStatus(plano.getId(), plano.getStatus());
        return plano;
    }

    private PlanoDeVoo getPlano(Integer id) {
        var plano = planoDeVooPort.getPlanoDeVoo(id);
        if (plano == null) {
            throw new PlanoNaoEncontradoException(id);
        }
        return plano;
    }

    private PlanoDeVoo getPlanoEmEdicao(Integer id, String mensagem) {
        var plano = getPlano(id);
        if (plano.getStatus() != Status.EDICAO) {
            throw new TransicaoInvalidaException(mensagem);
        }
        return plano;
    }
}
