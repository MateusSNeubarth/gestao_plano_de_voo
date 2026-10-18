package com.projarc.trabalhofinal.application.usecase.plano;

import com.projarc.trabalhofinal.domain.model.plano.PlanoDeVoo;
import com.projarc.trabalhofinal.domain.model.plano.Problema;
import com.projarc.trabalhofinal.domain.model.plano.VerificacaoDoPlano;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ValidadorDePlano {

    private final List<VerificacaoDoPlano> verificacoes;

    public List<Problema> validar(PlanoDeVoo plano) {
        return verificacoes.stream()
                .flatMap(verificacao -> verificacao.verificar(plano).stream())
                .toList();
    }
}
