package com.projarc.trabalhofinal.domain.model.reserva;

import com.projarc.trabalhofinal.domain.model.malha.Aerovia;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ReservaAerovia extends Reserva {

    private Aerovia aerovia;
    private Integer altitude;
}
