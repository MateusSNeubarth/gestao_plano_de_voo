package com.projarc.trabalhofinal.application.port.in.malha;


import com.projarc.trabalhofinal.domain.model.malha.Aeroporto;

import java.util.List;

public interface AeroportoInPort {
    List<Aeroporto> getAeroportos();
    Aeroporto getAeroporto(String codigo);
    void createAeroporto(Aeroporto aeroporto);
}
