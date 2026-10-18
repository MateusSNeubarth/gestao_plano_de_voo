package com.projarc.trabalhofinal.domain.model.reserva;

import com.projarc.trabalhofinal.domain.model.plano.PlanoDeVoo;

public interface ServicoDeReservas {

    void reservar(PlanoDeVoo plano);

    void liberar(PlanoDeVoo plano);
}
