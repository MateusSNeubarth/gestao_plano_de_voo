package com.projarc.trabalhofinal.application.port.in.plano;

import com.projarc.trabalhofinal.domain.model.plano.PlanoDeVoo;
import com.projarc.trabalhofinal.domain.model.plano.Problema;

import java.util.List;

public interface CicloDeVidaInPort {
    List<Problema> validar(Integer id);
    PlanoDeVoo aprovar(Integer id);
    PlanoDeVoo abandonar(Integer id);
    PlanoDeVoo cancelar(Integer id);
}
