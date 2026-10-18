package com.projarc.trabalhofinal.application.usecase.malha;

import com.projarc.trabalhofinal.application.port.in.malha.AeroviaInPort;
import com.projarc.trabalhofinal.application.port.out.malha.AeroviaPort;
import com.projarc.trabalhofinal.domain.model.malha.Aerovia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AeroviaUseCase implements AeroviaInPort {

    private final AeroviaPort aeroviaPort;

    @Override
    public List<Aerovia> getAerovias() {
        return aeroviaPort.getAerovias();
    }

    @Override
    public Aerovia getAerovia(String id) {
        return aeroviaPort.getAerovia(id);
    }

    @Override
    public void createAerovia(Aerovia aerovia) {
        aeroviaPort.createAerovia(aerovia);
    }
}
