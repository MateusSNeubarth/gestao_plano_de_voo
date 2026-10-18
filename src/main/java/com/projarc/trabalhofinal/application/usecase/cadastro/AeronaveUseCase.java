package com.projarc.trabalhofinal.application.usecase.cadastro;

import com.projarc.trabalhofinal.application.port.in.AeronaveInPort;
import com.projarc.trabalhofinal.application.port.out.AeronavesOutPort;
import com.projarc.trabalhofinal.domain.model.aeronave.Aeronave;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AeronaveUseCase implements AeronaveInPort {

    private final AeronavesOutPort aeronavesOutPort;

    @Override
    public List<Aeronave> listar() {
        return aeronavesOutPort.listar();
    }

    @Override
    public Aeronave buscarPorPrefixo(String prefixo) {
        return aeronavesOutPort.buscarPorPrefixo(prefixo);
    }

    @Override
    public Aeronave criarAeronave(Aeronave aeronave) {
        if (aeronavesOutPort.existe(aeronave.getPrefixo())) {
            throw new IllegalStateException("Já existe aeronave com o prefixo " + aeronave.getPrefixo());
        }
        return aeronavesOutPort.criarAeronave(aeronave);
    }
}