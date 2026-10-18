package com.projarc.trabalhofinal.adapter.out.persistence.entity.reserva;

import com.projarc.trabalhofinal.adapter.out.persistence.entity.malha.AeroviaEntity;
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
@DiscriminatorValue("AEROVIA")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReservaAeroviaEntity extends ReservaEntity {

    @ManyToOne
    @JoinColumn(name = "aerovia_id")
    private AeroviaEntity aerovia;
    private Integer altitude;
}
