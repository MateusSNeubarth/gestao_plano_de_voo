package com.projarc.trabalhofinal.adapter.in.rest.controller.plano;

import com.projarc.trabalhofinal.adapter.in.rest.dto.plano.request.TrechoRequest;
import com.projarc.trabalhofinal.adapter.in.rest.dto.plano.response.PlanoDeVooResponse;
import com.projarc.trabalhofinal.adapter.in.rest.mapper.PlanoRestMapper;
import com.projarc.trabalhofinal.application.port.in.plano.PlanoDeVooInPort;
import com.projarc.trabalhofinal.domain.exception.PlanoNaoEncontradoException;
import com.projarc.trabalhofinal.domain.exception.TransicaoInvalidaException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestController
@RequestMapping("/api/planos/{id}/percurso/trechos")
@RequiredArgsConstructor
public class TrechoController {

    private final PlanoDeVooInPort planoDeVoo;

    @PutMapping("/{ordem}")
    public ResponseEntity<PlanoDeVooResponse> alterarTrecho(@PathVariable Integer id, @PathVariable Integer ordem,
                                                            @RequestBody TrechoRequest request) {
        var plano = planoDeVoo.alterarTrecho(id, ordem, PlanoRestMapper.toDomain(request));
        return ResponseEntity.ok(PlanoRestMapper.fromDomain(plano));
    }

    @ExceptionHandler(PlanoNaoEncontradoException.class)
    public ResponseEntity<Void> planoNaoEncontrado() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(TransicaoInvalidaException.class)
    public ResponseEntity<Void> planoForaDeEdicao() {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Void> requisicaoRejeitada() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> dadosInvalidos() {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
    }
}
