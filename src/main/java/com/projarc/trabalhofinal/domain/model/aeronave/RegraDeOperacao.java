package com.projarc.trabalhofinal.domain.model.aeronave;

import java.time.LocalDateTime;

public interface RegraDeOperacao {

    boolean permite(int altitude, LocalDateTime entrada, LocalDateTime saida);
}
