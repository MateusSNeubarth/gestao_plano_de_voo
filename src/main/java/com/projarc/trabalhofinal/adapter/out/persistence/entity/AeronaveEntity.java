package com.projarc.trabalhofinal.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "aeronave")
@NoArgsConstructor
@AllArgsConstructor
public class AeronaveEntity {

    @Id
    private String prefixo;
    @Column(nullable = false)
    private String categoria;
    @Column(nullable = false)
    private Integer velocidadeDeCruzeiro;
    @Column(nullable = false)
    private Integer autonomia;

    // particular
    private String empresaResponsavelPelaManutencao;

    // comercial (passageiros e carga)
    private String companhiaAerea;

    // comercial de passageiros
    private Integer capacidadeMaxPassageiro;

    // comercial de carga
    private Double capacidadeMaxCargaTn;
}