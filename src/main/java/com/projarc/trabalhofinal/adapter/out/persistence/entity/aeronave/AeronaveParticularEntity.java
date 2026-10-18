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
@DiscriminatorValue("PARTICULAR")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AeronaveParticularEntity extends AeronaveEntity {

    @Column(name = "empresa_responsavel_pela_manutencao")
    private String empresaResponsavelPelaManutencao;
}
