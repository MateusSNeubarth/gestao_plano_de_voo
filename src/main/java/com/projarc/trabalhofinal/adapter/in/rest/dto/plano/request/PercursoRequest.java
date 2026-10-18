package com.projarc.trabalhofinal.adapter.in.rest.dto.plano.request;

import java.util.List;

public record PercursoRequest(
        List<TrechoRequest> trechos
) {
}
