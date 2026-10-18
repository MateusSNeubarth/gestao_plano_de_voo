package com.projarc.trabalhofinal.adapter.in.rest.dto;

public record AeronaveRequest(String prefixo, String categoria, Integer velocidadeDeCruzeiro,
        Integer autonomia, String empresaResponsavelPelaManutencao, String companhiaAerea,
        Integer capacidadeMaxPassageiro, Double capacidadeMaxCargaTn
) {}