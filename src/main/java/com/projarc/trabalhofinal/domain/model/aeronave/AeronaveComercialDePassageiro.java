package com.projarc.trabalhofinal.domain.model.aeronave;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AeronaveComercialDePassageiro extends Aeronave {

    private String companhiaAerea;
    private Integer capacidadeMaxPassageiro;
}
