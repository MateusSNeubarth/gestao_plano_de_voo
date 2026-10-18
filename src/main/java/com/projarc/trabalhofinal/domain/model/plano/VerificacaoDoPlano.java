package com.projarc.trabalhofinal.domain.model.plano;

import java.util.List;

public interface VerificacaoDoPlano {

    List<Problema> verificar(PlanoDeVoo plano);
}
