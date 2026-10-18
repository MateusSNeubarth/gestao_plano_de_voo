package com.projarc.trabalhofinal.domain.model.aeronave;

import lombok.Getter;
import java.util.List;

@Getter
public class AeronaveParticular extends Aeronave {

    private String empresaResponsavelPelaManutencao;

    public AeronaveParticular(String prefixo, Integer velocidadeDeCruzeiro, Integer autonomia, String empresaResponsavelPelaManutencao) {
        super(prefixo, velocidadeDeCruzeiro, autonomia);
        this.empresaResponsavelPelaManutencao = empresaResponsavelPelaManutencao;
    }
}
