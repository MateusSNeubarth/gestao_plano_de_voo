package com.projarc.trabalhofinal.domain.model.aeronave;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AeronaveParticular extends Aeronave {

    private String empresaResponsavelPelaManutencao;
}
