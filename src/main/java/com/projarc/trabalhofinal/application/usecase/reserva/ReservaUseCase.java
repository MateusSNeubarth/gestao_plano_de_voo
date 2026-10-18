package com.projarc.trabalhofinal.application.usecase.reserva;

import com.projarc.trabalhofinal.application.port.out.reserva.ReservaPort;
import com.projarc.trabalhofinal.domain.model.plano.PlanoDeVoo;
import com.projarc.trabalhofinal.domain.model.reserva.ServicoDeReservas;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ReservaUseCase implements ServicoDeReservas {

    private final ReservaPort reservaPort;

    @Override
    public void reservar(PlanoDeVoo plano) {
        plano.setReservas(new ArrayList<>(reservaPort.createReservas(plano.getId(), plano.reservasNecessarias())));
    }

    @Override
    public void liberar(PlanoDeVoo plano) {
        reservaPort.deleteReservas(plano.getId());
        plano.setReservas(new ArrayList<>());
    }
}
