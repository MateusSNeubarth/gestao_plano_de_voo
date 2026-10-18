package com.projarc.trabalhofinal.domain.exception;

import com.projarc.trabalhofinal.domain.model.plano.Problema;
import lombok.Getter;

import java.util.List;

@Getter
public class PlanoInvalidoException extends RuntimeException {

    private final List<Problema> problemas;

    public PlanoInvalidoException(List<Problema> problemas) {
        super("O plano de voo possui problemas de validação");
        this.problemas = problemas;
    }
}
