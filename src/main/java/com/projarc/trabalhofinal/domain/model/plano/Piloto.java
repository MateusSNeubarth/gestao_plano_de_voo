package com.projarc.trabalhofinal.domain.model.plano;

import lombok.Getter;

@Getter
public class Piloto {

    private Integer id;
    private String nome;

    public Piloto(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}
