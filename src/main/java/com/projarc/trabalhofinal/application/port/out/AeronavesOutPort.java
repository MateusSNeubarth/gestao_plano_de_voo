package com.projarc.trabalhofinal.application.port.out;

import com.projarc.trabalhofinal.domain.model.aeronave.Aeronave;

import java.util.List;

public interface AeronavesOutPort {
    List<Aeronave> listar();
    Aeronave buscarPorPrefixo(String prefixo);
    Aeronave criarAeronave(Aeronave aeronave);
    boolean existe(String prefixo);
}
