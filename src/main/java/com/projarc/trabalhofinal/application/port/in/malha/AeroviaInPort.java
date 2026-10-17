package com.projarc.trabalhofinal.application.port.in.malha;

import com.projarc.trabalhofinal.domain.model.malha.Aerovia;

import java.util.List;

public interface AeroviaInPort {
    List<Aerovia> getAerovias();
    Aerovia getAerovia(String id);
    void createAerovia(Aerovia aerovia);
}
