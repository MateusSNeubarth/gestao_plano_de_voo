package com.projarc.trabalhofinal.adapter.in.rest.controller.malha;

import com.projarc.trabalhofinal.adapter.in.rest.dto.malha.request.AeroviaRequest;
import com.projarc.trabalhofinal.adapter.in.rest.dto.malha.response.AeroviaResponse;
import com.projarc.trabalhofinal.adapter.in.rest.mapper.MalhaRestMapper;
import com.projarc.trabalhofinal.application.port.in.malha.AeroviaInPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/aerovias")
@RequiredArgsConstructor
public class AeroviaController {

    private final AeroviaInPort aeroviaInPort;

    @GetMapping
    public ResponseEntity<List<AeroviaResponse>> getAerovias() {
        return ResponseEntity.ok(aeroviaInPort.getAerovias().stream()
                .map(MalhaRestMapper::fromDomain)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AeroviaResponse> getAerovia(@PathVariable String id) {
        return ResponseEntity.ok(MalhaRestMapper.fromDomain(aeroviaInPort.getAerovia(id)));
    }

    @PostMapping
    public ResponseEntity<Void> createAerovia(@RequestBody AeroviaRequest request) {
        aeroviaInPort.createAerovia(MalhaRestMapper.toDomain(request));
        return ResponseEntity.created(URI.create("/api/aerovias/" + request.id())).build();
    }
}
