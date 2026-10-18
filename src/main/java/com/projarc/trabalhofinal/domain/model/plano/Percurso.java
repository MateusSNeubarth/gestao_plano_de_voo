package com.projarc.trabalhofinal.domain.model.plano;

import com.projarc.trabalhofinal.domain.model.malha.Aeroporto;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Percurso {

    @Builder.Default
    private List<Trecho> trechos = new ArrayList<>();

    public void validarContinuidade(Aeroporto origem, Aeroporto destino) {
        throw new UnsupportedOperationException("TODO");
    }
}
