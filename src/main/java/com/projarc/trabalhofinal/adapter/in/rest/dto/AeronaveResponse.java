package com.projarc.trabalhofinal.adapter.in.rest.dto;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AeronaveResponse(String prefixo, String categoria,
                               Integer velocidadeCruzeiroKmH, Integer autonomiaNominalKm,
                               String empresaManutencao, String companhiaAerea,
                               Integer capacidadeMaximaPassageiros, Double capacidadeMaximaToneladas) {}

