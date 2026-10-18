package com.projarc.trabalhofinal.domain.model.reserva;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public record Slot(LocalDateTime inicio) {

    public static List<Slot> tocadosPor(LocalDateTime inicio, LocalDateTime fim) {
        List<Slot> slots = new ArrayList<>();
        for (var hora = inicio.truncatedTo(ChronoUnit.HOURS); hora.isBefore(fim); hora = hora.plusHours(1)) {
            slots.add(new Slot(hora));
        }
        return slots;
    }
}
