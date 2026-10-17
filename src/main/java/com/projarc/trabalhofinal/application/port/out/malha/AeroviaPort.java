package com.projarc.trabalhofinal.application.port.out.malha;

import com.projarc.trabalhofinal.domain.model.malha.Aerovia;

import java.util.List;

public interface AeroviaPort {
    List<Aerovia> getAerovias();
    Aerovia getAerovia(String id);
    void createAerovia(Aerovia aerovia);
}
