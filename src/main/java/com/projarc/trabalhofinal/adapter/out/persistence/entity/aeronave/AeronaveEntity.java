package com.projarc.trabalhofinal.adapter.out.persistence.entity.aeronave;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@Entity
@Table(name = "aeronave")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "categoria")
@NoArgsConstructor
public abstract class AeronaveEntity {

    @Id
    private String prefixo;
    @Column(nullable = false, name = "velocidade_de_cruzeiro")
    private Integer velocidadeDeCruzeiro;
    @Column(nullable = false)
    private Integer autonomia;
}
