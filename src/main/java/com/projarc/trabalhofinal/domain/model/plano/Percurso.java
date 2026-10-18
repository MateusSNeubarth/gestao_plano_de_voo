
package com.projarc.trabalhofinal.domain.model.plano;

import com.projarc.trabalhofinal.domain.model.malha.Aeroporto;
import com.projarc.trabalhofinal.domain.model.malha.Aerovia;
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
        if (origem == null || destino == null) {
            throw new IllegalArgumentException(
                    "A origem e o destino do plano são obrigatórios."
            );
        }

        if (trechos == null || trechos.isEmpty()) {
            throw new IllegalArgumentException(
                    "O percurso deve possuir pelo menos um trecho."
            );
        }

        if (origem.getCodigo() == null || destino.getCodigo() == null) {
            throw new IllegalArgumentException(
                    "Os códigos dos aeroportos são obrigatórios."
            );
        }

        String codigoAeroportoAtual = origem.getCodigo();

        for (Trecho trecho : trechos) {
            if (trecho == null || trecho.getAerovia() == null) {
                throw new IllegalArgumentException(
                        "O percurso contém um trecho ou uma aerovia inválida."
                );
            }

            Aerovia aerovia = trecho.getAerovia();

            if (aerovia.getCodOrigem() == null
                    || aerovia.getCodDestino() == null) {
                throw new IllegalArgumentException(
                        "A aerovia deve possuir origem e destino."
                );
            }

            if (!codigoAeroportoAtual.equals(aerovia.getCodOrigem())) {
                throw new IllegalArgumentException(
                        "Percurso descontínuo: a origem da aerovia "
                                + aerovia.getId()
                                + " não corresponde ao aeroporto atual."
                );
            }

            codigoAeroportoAtual = aerovia.getCodDestino();
        }

        if (!codigoAeroportoAtual.equals(destino.getCodigo())) {
            throw new IllegalArgumentException(
                    "O percurso não termina no aeroporto de destino do plano."
            );
        }
    }
    public void substituirTrechos(List<Trecho> novosTrechos) {
        if (novosTrechos == null) {
            throw new IllegalArgumentException(
                    "A lista de trechos não pode ser nula."
            );
        }

        this.trechos = new ArrayList<>(novosTrechos); // cria uma nova lista para evitar que as alterações
        // na lista recebida modifiquem diretamente a lista interna do percurso.
    }
}
