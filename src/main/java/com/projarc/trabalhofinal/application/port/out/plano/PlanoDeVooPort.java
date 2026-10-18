package com.projarc.trabalhofinal.application.port.out.plano;

import com.projarc.trabalhofinal.domain.model.plano.Percurso;
import com.projarc.trabalhofinal.domain.model.plano.PlanoDeVoo;
import com.projarc.trabalhofinal.domain.model.plano.Status;

import java.util.List;

public interface PlanoDeVooPort {
    PlanoDeVoo criarPlano(PlanoDeVoo plano);
    List<PlanoDeVoo> listarPlanosPorStatus(Status status);
    PlanoDeVoo getPlanoDeVoo(Integer id);
    PlanoDeVoo updatePercurso(Integer id, Percurso percurso);
    void updateStatus(Integer id, Status status);
}
