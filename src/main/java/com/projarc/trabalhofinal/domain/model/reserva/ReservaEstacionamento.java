package com.projarc.trabalhofinal.domain.model.reserva;

import com.projarc.trabalhofinal.domain.model.malha.Aeroporto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ReservaEstacionamento extends Reserva {

    private Aeroporto aeroporto;
    private Integer numeroVaga;
}
