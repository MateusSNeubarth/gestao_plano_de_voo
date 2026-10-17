package com.projarc.trabalhofinal.adapter.in.rest.config.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "seguranca.usuarios")
public record CredenciaisProperties(Credencial admin, Credencial usuario) {

    public record Credencial(String username, String password) {}
}
