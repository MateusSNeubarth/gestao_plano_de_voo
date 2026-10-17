package com.projarc.trabalhofinal.application.usecase.malha;

import com.projarc.trabalhofinal.application.port.in.malha.AeroportoInPort;
import com.projarc.trabalhofinal.application.port.out.malha.AeroportoPort;
import com.projarc.trabalhofinal.domain.model.malha.Aeroporto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AeronavesUseCase implements AeroportoInPort {

    private final AeroportoPort aeroportoPort;

    @Override
    public List<Aeroporto> getAeroportos() {
        return aeroportoPort.getAeroportos();
    }

    @Override
    public Aeroporto getAeroporto(String codigo) {
        return aeroportoPort.getAeroporto(codigo);
    }

    @Override
    public void createAeroporto(Aeroporto aeroporto) {
        aeroportoPort.createAeroporto(aeroporto);
    }
}
