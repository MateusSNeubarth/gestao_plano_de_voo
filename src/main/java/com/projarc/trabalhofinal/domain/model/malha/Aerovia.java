package com.projarc.trabalhofinal.domain.model.malha;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Aerovia {

    private String id;
    private Integer distanciaKm;
    private String codOrigem;
    private String codDestino;
}
