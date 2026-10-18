package com.projarc.trabalhofinal.adapter.out.persistence.entity.aeronave;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@Entity
@DiscriminatorValue("COMERCIAL_DE_CARGA")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AeronaveComercialDeCargaEntity extends AeronaveEntity {

    @Column(name = "companhia_aerea")
    private String companhiaAerea;
    @Column(name = "capacidade_max_carga_tn")
    private Double capacidadeMaxCargaTn;
}
