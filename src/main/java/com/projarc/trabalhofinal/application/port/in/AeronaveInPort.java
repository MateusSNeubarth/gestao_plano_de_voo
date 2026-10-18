package com.projarc.trabalhofinal.application.port.in;

import com.projarc.trabalhofinal.domain.model.aeronave.Aeronave;

import java.util.List;

public interface AeronaveInPort {
    List<Aeronave> listar();
    Aeronave buscarPorPrefixo(String prefixo);
    Aeronave criarAeronave(Aeronave aeronave);
}
