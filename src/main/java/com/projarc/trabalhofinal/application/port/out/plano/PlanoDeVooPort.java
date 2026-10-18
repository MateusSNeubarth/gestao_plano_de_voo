package com.projarc.trabalhofinal.application.port.out.plano;

import com.projarc.trabalhofinal.domain.model.plano.PlanoDeVoo;
import com.projarc.trabalhofinal.domain.model.plano.Status;

public interface PlanoDeVooPort {
    PlanoDeVoo getPlanoDeVoo(Integer id);
    void updateStatus(Integer id, Status status);
}
