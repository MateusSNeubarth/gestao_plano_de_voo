package com.projarc.trabalhofinal.adapter.in.rest.controller;

import com.projarc.trabalhofinal.adapter.in.rest.dto.PilotoRequest;
import com.projarc.trabalhofinal.adapter.in.rest.dto.PilotoResponse;
import com.projarc.trabalhofinal.adapter.in.rest.mapper.PilotoRestMapper;
import com.projarc.trabalhofinal.application.port.in.PilotoInPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pilotos")
public class PilotoController {
    private final PilotoInPort pilotoInPort;

    public PilotoController(PilotoInPort pilotoInPort) { this.pilotoInPort = pilotoInPort; }

    @GetMapping
    public ResponseEntity<List<PilotoResponse>> consultar() {
        return ResponseEntity.ok(pilotoInPort.listar().stream().map(PilotoRestMapper::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PilotoResponse> consultarId(@PathVariable Integer id){
        var domain = pilotoInPort.buscarPorId(id);
        if (domain == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(PilotoRestMapper.toResponse(domain));
    }

    @PostMapping
    public ResponseEntity<PilotoResponse> criarPiloto(@RequestBody PilotoRequest request) {
        var piloto = PilotoRestMapper.toDomain(request);
        var criada = pilotoInPort.criarPiloto(piloto);
        return ResponseEntity
                .created(URI.create("/pilotos/" + criada.getId()))
                .body(PilotoRestMapper.toResponse(criada));
    }
}
