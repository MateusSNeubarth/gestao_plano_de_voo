package com.projarc.trabalhofinal.application.port.in.plano;

import com.projarc.trabalhofinal.domain.model.plano.PlanoDeVoo;
import com.projarc.trabalhofinal.domain.model.plano.Status;
import com.projarc.trabalhofinal.domain.model.plano.Trecho;

import java.util.List;

public interface PlanoDeVooInPort {

    PlanoDeVoo criarPlano(PlanoDeVoo plano);

    PlanoDeVoo consultarPlano(Integer id);

    List<PlanoDeVoo> listarPlanosPorStatus(Status status);

    PlanoDeVoo substituirPercurso(Integer id, List<Trecho> trechos);

    PlanoDeVoo alterarTrecho(Integer id, int ordem, Trecho trecho);
}
