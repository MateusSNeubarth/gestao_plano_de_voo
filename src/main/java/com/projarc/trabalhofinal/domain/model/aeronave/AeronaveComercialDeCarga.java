package com.projarc.trabalhofinal.domain.model.aeronave;

import lombok.Getter;
import java.util.List;

@Getter
public class AeronaveComercialDeCarga extends Aeronave {

    private String companhiaAerea;
    private Double capacidadeMaxCargaTn;

    public AeronaveComercialDeCarga(String prefixo, Integer velocidadeDeCruzeiro, Integer autonomia, String companhiaAerea, Double capacidadeMaxCargaTn) {
        super(prefixo, velocidadeDeCruzeiro, autonomia);
        this.companhiaAerea = companhiaAerea;
        this.capacidadeMaxCargaTn = capacidadeMaxCargaTn;
    }
}
