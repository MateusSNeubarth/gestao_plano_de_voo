package com.projarc.trabalhofinal.application.port.in.plano;

import com.projarc.trabalhofinal.domain.model.plano.PlanoDeVoo;
import com.projarc.trabalhofinal.domain.model.plano.Status;

import java.util.List;

public interface PlanoDeVooInPort {

    PlanoDeVoo criarPlano(PlanoDeVoo plano);

    PlanoDeVoo consultarPlano(Integer id);

    List<PlanoDeVoo> listarPlanosPorStatus(Status status);
}
