package com.projarc.trabalhofinal.adapter.in.rest.dto.plano.response;

import lombok.Builder;

@Builder
public record ProblemaResponse(
        String codigo,
        String mensagem
) {
}
