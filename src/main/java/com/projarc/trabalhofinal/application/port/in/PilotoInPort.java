package com.projarc.trabalhofinal.application.port.in;

import com.projarc.trabalhofinal.domain.model.plano.Piloto;

import java.util.List;

public interface PilotoInPort {
    List<Piloto> listar();
    Piloto buscarPorId(Integer id);
    Piloto criarPiloto(Piloto piloto);
}
