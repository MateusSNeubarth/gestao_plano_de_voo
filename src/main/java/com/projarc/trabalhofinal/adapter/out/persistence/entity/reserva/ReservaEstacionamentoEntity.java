package com.projarc.trabalhofinal.adapter.out.persistence.entity.reserva;

import com.projarc.trabalhofinal.adapter.out.persistence.entity.malha.AeroportoEntity;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@Entity
@DiscriminatorValue("ESTACIONAMENTO")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReservaEstacionamentoEntity extends ReservaEntity {

    @ManyToOne
    @JoinColumn(name = "codigo_aeroporto")
    private AeroportoEntity aeroporto;
    @Column(name = "numero_vaga")
    private Integer numeroVaga;
}
