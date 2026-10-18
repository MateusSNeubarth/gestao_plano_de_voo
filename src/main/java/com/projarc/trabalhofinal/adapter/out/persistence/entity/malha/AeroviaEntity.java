package com.projarc.trabalhofinal.adapter.out.persistence.entity.malha;

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
@Table(name = "aerovia")
@NoArgsConstructor
@AllArgsConstructor
public class AeroviaEntity {

    @Id
    private String id;

    @Column(name = "distancia_km")
    private Integer distanciaKm;

    @Column(name = "codigo_origem")
    private String codigoOrigem;

    @Column(name = "codigo_destino")
    private String codigoDestino;
}
