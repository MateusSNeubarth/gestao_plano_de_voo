package com.projarc.trabalhofinal.application.usecase.cadastro;

import com.projarc.trabalhofinal.application.port.in.AeronaveInPort;
import com.projarc.trabalhofinal.application.port.out.AeronavesOutPort;
import com.projarc.trabalhofinal.domain.model.aeronave.Aeronave;
import com.projarc.trabalhofinal.domain.model.aeronave.AeronaveComercialDeCarga;
import com.projarc.trabalhofinal.domain.model.aeronave.AeronaveComercialDePassageiro;
import com.projarc.trabalhofinal.domain.model.aeronave.AeronaveParticular;
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
        validar(aeronave);
        if (aeronavesOutPort.existe(aeronave.getPrefixo())) {
            throw new IllegalStateException("Já existe aeronave com o prefixo " + aeronave.getPrefixo());
        }
        return aeronavesOutPort.criarAeronave(aeronave);
    }

    private void validar(Aeronave aeronave) {
        if (aeronave.getPrefixo() == null || aeronave.getPrefixo().isBlank()
                || aeronave.getVelocidadeDeCruzeiro() == null || aeronave.getAutonomia() == null) {
            throw new IllegalArgumentException("Prefixo, velocidade e autonomia são obrigatórios");
        }
        if (aeronave instanceof AeronaveParticular a && a.getEmpresaResponsavelPelaManutencao() == null) {
            throw new IllegalArgumentException("Particular exige empresa de manutenção");
        }
        if (aeronave instanceof AeronaveComercialDePassageiro a
                && (a.getCompanhiaAerea() == null || a.getCapacidadeMaxPassageiro() == null)) {
            throw new IllegalArgumentException("Passageiros exige companhia aérea e capacidade de passageiros");
        }
        if (aeronave instanceof AeronaveComercialDeCarga a
                && (a.getCompanhiaAerea() == null || a.getCapacidadeMaxCargaTn() == null)) {
            throw new IllegalArgumentException("Carga exige companhia aérea e capacidade de carga");
        }
    }

}