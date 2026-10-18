package com.projarc.trabalhofinal.config;

import com.projarc.trabalhofinal.domain.model.aeronave.CatalogoDeRegras;
import com.projarc.trabalhofinal.domain.model.aeronave.CatalogoDeRegrasPadrao;
import com.projarc.trabalhofinal.domain.model.plano.VerificacaoDeAutonomia;
import com.projarc.trabalhofinal.domain.model.plano.VerificacaoDeOperacao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RegrasDeOperacaoConfig {

    @Bean
    public CatalogoDeRegras catalogoDeRegras() {
        return new CatalogoDeRegrasPadrao();
    }

    @Bean
    public VerificacaoDeOperacao verificacaoDeOperacao(CatalogoDeRegras catalogo) {
        return new VerificacaoDeOperacao(catalogo);
    }

    @Bean
    public VerificacaoDeAutonomia verificacaoDeAutonomia() {
        return new VerificacaoDeAutonomia();
    }
}
