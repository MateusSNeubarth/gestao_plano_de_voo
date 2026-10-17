package com.projarc.trabalhofinal.adapter.in.rest.config.security;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableConfigurationProperties(CredenciaisProperties.class)
public class SecurityConfig {

    private static final String[] CADASTROS = {
        "/api/aeroportos/**",
        "/api/aerovias/**",
        "/api/pilotos/**",
        "/api/aeronaves/**"
    };

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public UserDetailsService userDetailsService(CredenciaisProperties credenciais, PasswordEncoder passwordEncoder) {
        return new InMemoryUserDetailsManager(
            usuario(credenciais.admin(), TipoUsuario.ADMIN, passwordEncoder),
            usuario(credenciais.usuario(), TipoUsuario.USUARIO, passwordEncoder)
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, CADASTROS).hasRole(TipoUsuario.ADMIN.name())
                .requestMatchers(HttpMethod.PUT, CADASTROS).hasRole(TipoUsuario.ADMIN.name())
                .requestMatchers(HttpMethod.PATCH, CADASTROS).hasRole(TipoUsuario.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, CADASTROS).hasRole(TipoUsuario.ADMIN.name())
                .anyRequest().hasAnyRole(TipoUsuario.ADMIN.name(), TipoUsuario.USUARIO.name()))
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    private UserDetails usuario(CredenciaisProperties.Credencial credencial, TipoUsuario tipo, PasswordEncoder passwordEncoder) {
        return User.withUsername(credencial.username())
            .password(passwordEncoder.encode(credencial.password()))
            .roles(tipo.name())
            .build();
    }
}
