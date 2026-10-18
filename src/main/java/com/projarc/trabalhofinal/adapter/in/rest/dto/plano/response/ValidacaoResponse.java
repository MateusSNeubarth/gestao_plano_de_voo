package com.projarc.trabalhofinal.adapter.in.rest.dto.plano.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ValidacaoResponse(
        boolean valido,
        List<ProblemaResponse> problemas
) {
}
