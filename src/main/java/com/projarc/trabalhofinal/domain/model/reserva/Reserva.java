package com.projarc.trabalhofinal.domain.model.reserva;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public abstract class Reserva {

    private Integer id;
    private LocalDateTime slot;
}
