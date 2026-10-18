package com.projarc.trabalhofinal.adapter.in.rest.controller;

import com.projarc.trabalhofinal.adapter.in.rest.dto.AeronaveRequest;
import com.projarc.trabalhofinal.adapter.in.rest.dto.AeronaveResponse;
import com.projarc.trabalhofinal.adapter.in.rest.mapper.AeronaveRestMapper;
import com.projarc.trabalhofinal.application.port.in.AeronaveInPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/aeronaves")
public class AeronaveController {
    private final AeronaveInPort consultarAeronaves;

    public AeronaveController(AeronaveInPort consultarAeronaves) { this.consultarAeronaves = consultarAeronaves; }

    @GetMapping
    public List<AeronaveResponse> consultar() {
        return consultarAeronaves.listar().stream().map(AeronaveRestMapper::toResponse).toList();
    }

    @GetMapping("/{prefixo}")
    public ResponseEntity<AeronaveResponse> consultarPrefixo(@PathVariable String prefixo){
        var domain = consultarAeronaves.buscarPorPrefixo(prefixo);
        if (domain == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(AeronaveRestMapper.toResponse(domain));
    }


    @PostMapping
    public ResponseEntity<AeronaveResponse> criarAeronave(@RequestBody AeronaveRequest request) {
        try {
            var aeronave = AeronaveRestMapper.toDomain(request);
            var criada = consultarAeronaves.criarAeronave(aeronave);
            return ResponseEntity
                    .created(URI.create("/api/aeronaves/" + criada.getPrefixo()))
                    .body(AeronaveRestMapper.toResponse(criada));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

}
