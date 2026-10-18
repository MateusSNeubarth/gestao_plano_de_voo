package com.projarc.trabalhofinal.domain.model.plano;

import com.projarc.trabalhofinal.domain.model.malha.Aerovia;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
@Builder
public class Trecho {

    private Aerovia aerovia;
    private Integer altitude;
    private LocalDateTime horarioEntradaNaAerovia;
    private AcaoNaChegada acaoNaChegada;
    private Integer numeroVagaPretendida;
    private Integer tempoPermanenciaMinutos;

    public LocalDateTime chegada(int velocidadeKmH) {
        long minutos = (long) Math.ceil(aerovia.getDistanciaKm() * 60.0 / velocidadeKmH);
        return horarioEntradaNaAerovia.plusMinutes(minutos);
    }

    public Optional<LocalDateTime> fimDaPermanencia(int velocidadeKmH) {
        if (acaoNaChegada != AcaoNaChegada.POUSO || tempoPermanenciaMinutos == null) {
            return Optional.empty();
        }
        return Optional.of(chegada(velocidadeKmH).plusMinutes(tempoPermanenciaMinutos));
    }
}
