package com.projarc.trabalhofinal.domain.model.aeronave;

import java.util.List;

public interface CatalogoDeRegras {

    List<RegraDeOperacao> regrasPara(Aeronave aeronave);
}
