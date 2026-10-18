package com.projarc.trabalhofinal.domain.model.aeronave;

import lombok.Getter;
import java.util.List;

@Getter
public class AeronaveComercialDePassageiro extends Aeronave {

    private String companhiaAerea;
    private Integer capacidadeMaxPassageiro;

    public AeronaveComercialDePassageiro(String prefixo, Integer velocidadeDeCruzeiro, Integer autonomia, String companhiaAerea, Integer capacidadeMaxPassageiro) {
        super(prefixo, velocidadeDeCruzeiro, autonomia);
        this.companhiaAerea = companhiaAerea;
        this.capacidadeMaxPassageiro = capacidadeMaxPassageiro;
    }
}
