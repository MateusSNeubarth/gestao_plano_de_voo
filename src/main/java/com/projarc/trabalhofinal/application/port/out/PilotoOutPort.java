package com.projarc.trabalhofinal.application.port.out;

import com.projarc.trabalhofinal.domain.model.plano.Piloto;

import java.util.List;

public interface PilotoOutPort {
    List<Piloto> listar();
    Piloto buscarPorId(Integer id);
    Piloto criarPiloto(Piloto piloto);
}
