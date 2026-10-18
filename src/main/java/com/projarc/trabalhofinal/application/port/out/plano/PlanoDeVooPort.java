package com.projarc.trabalhofinal.application.port.out.plano;

import com.projarc.trabalhofinal.domain.model.plano.PlanoDeVoo;
import com.projarc.trabalhofinal.domain.model.plano.Status;

import java.util.List;

public interface PlanoDeVooPort {
    PlanoDeVoo criarPlano(PlanoDeVoo plano);
    PlanoDeVoo consultarPlano(Integer id);
    List<PlanoDeVoo> listarPlanosPorStatus(Status status);
    PlanoDeVoo getPlanoDeVoo(Integer id);
    void updateStatus(Integer id, Status status);
}
