package com.projarc.trabalhofinal.adapter.in.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AeronaveResponse(String prefixo, String categoria,
                               Integer velocidadeDeCruzeiro, Integer autonomia,
                               String empresaResponsavelPelaManutencao, String companhiaAerea,
                               Integer capacidadeMaxPassageiro, Double capacidadeMaxCargaTn) {}

