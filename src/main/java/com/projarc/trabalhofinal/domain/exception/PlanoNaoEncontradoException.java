package com.projarc.trabalhofinal.domain.exception;

public class PlanoNaoEncontradoException extends RuntimeException {

    public PlanoNaoEncontradoException(Integer id) {
        super("Plano de voo não encontrado: " + id);
    }
}
