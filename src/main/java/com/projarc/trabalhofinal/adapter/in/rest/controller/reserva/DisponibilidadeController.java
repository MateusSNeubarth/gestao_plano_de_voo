package com.projarc.trabalhofinal.adapter.in.rest.controller.reserva;

import com.projarc.trabalhofinal.adapter.in.rest.dto.reserva.response.AltitudesDisponiveisResponse;
import com.projarc.trabalhofinal.adapter.in.rest.dto.reserva.response.VagasDisponiveisResponse;
import com.projarc.trabalhofinal.adapter.in.rest.mapper.ReservaRestMapper;
import com.projarc.trabalhofinal.application.port.in.malha.AeroportoInPort;
import com.projarc.trabalhofinal.application.port.in.malha.AeroviaInPort;
import com.projarc.trabalhofinal.application.port.in.reserva.DisponibilidadeInPort;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DisponibilidadeController {

    private final DisponibilidadeInPort disponibilidadeInPort;
    private final AeroviaInPort aeroviaInPort;
    private final AeroportoInPort aeroportoInPort;

    @GetMapping("/aerovias/{id}/altitudes-disponiveis")
    public ResponseEntity<AltitudesDisponiveisResponse> getAltitudesDisponiveis(
            @PathVariable String id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        if (!inicio.isBefore(fim)) {
            return ResponseEntity.badRequest().build();
        }
        var aerovia = aeroviaInPort.getAerovia(id);
        if (aerovia == null) {
            return ResponseEntity.notFound().build();
        }
        var altitudes = disponibilidadeInPort.getAltitudesDisponiveis(aerovia, inicio, fim);
        return ResponseEntity.ok(ReservaRestMapper.toAltitudesDisponiveis(aerovia, inicio, fim, altitudes));
    }

    @GetMapping("/aeroportos/{codigo}/vagas-disponiveis")
    public ResponseEntity<VagasDisponiveisResponse> getVagasDisponiveis(
            @PathVariable String codigo,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        if (!inicio.isBefore(fim)) {
            return ResponseEntity.badRequest().build();
        }
        var aeroporto = aeroportoInPort.getAeroporto(codigo);
        if (aeroporto == null) {
            return ResponseEntity.notFound().build();
        }
        var vagas = disponibilidadeInPort.getVagasDisponiveis(aeroporto, inicio, fim);
        return ResponseEntity.ok(ReservaRestMapper.toVagasDisponiveis(aeroporto, inicio, fim, vagas));
    }
}
