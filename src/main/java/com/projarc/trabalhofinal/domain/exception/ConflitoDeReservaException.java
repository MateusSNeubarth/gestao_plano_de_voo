package com.projarc.trabalhofinal.domain.exception;

public class ConflitoDeReservaException extends RuntimeException {

    public ConflitoDeReservaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
