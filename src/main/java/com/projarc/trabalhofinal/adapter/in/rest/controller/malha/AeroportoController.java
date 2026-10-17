package com.projarc.trabalhofinal.adapter.in.rest.controller.malha;

import com.projarc.trabalhofinal.adapter.in.rest.dto.malha.request.AeroportoRequest;
import com.projarc.trabalhofinal.adapter.in.rest.dto.malha.response.AeroportoResponse;
import com.projarc.trabalhofinal.adapter.in.rest.mapper.MalhaRestMapper;
import com.projarc.trabalhofinal.application.port.in.malha.AeroportoInPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/aeroportos")
@RequiredArgsConstructor
public class AeroportoController {

    private final AeroportoInPort aeronaves;

    @GetMapping
    public ResponseEntity<List<AeroportoResponse>> getAeroportos() {
        return ResponseEntity.ok(
                aeronaves.getAeroportos().stream()
                        .map(MalhaRestMapper::fromDomain)
                        .toList()
        );
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<AeroportoResponse> getAeroporto(@PathVariable String codigo) {
        var domain = aeronaves.getAeroporto(codigo);
        if (domain == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(MalhaRestMapper.fromDomain(domain));
    }

    @PostMapping
    public ResponseEntity<Void> postAeroporto(@RequestBody AeroportoRequest request) {
        var existing = this.aeronaves.getAeroporto(request.codigo());
        if (existing != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        try {
            this.aeronaves.createAeroporto(MalhaRestMapper.toDomain(request));
            return ResponseEntity.created(URI.create("/aeroportos/" + request.codigo())).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
