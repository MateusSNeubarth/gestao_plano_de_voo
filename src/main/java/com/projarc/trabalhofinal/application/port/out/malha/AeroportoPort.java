package com.projarc.trabalhofinal.application.port.out.malha;

import com.projarc.trabalhofinal.domain.model.malha.Aeroporto;

import java.util.List;

public interface AeroportoPort {
    List<Aeroporto> getAeroportos();
    Aeroporto getAeroporto(String codigo);
    void createAeroporto(Aeroporto aeroporto);
}
