package com.projarc.trabalhofinal.adapter.in.rest.controller.plano;

import com.projarc.trabalhofinal.adapter.in.rest.dto.plano.response.SituacaoDoPlanoResponse;
import com.projarc.trabalhofinal.adapter.in.rest.dto.plano.response.ValidacaoResponse;
import com.projarc.trabalhofinal.adapter.in.rest.mapper.CicloDeVidaRestMapper;
import com.projarc.trabalhofinal.application.port.in.plano.CicloDeVidaInPort;
import com.projarc.trabalhofinal.domain.exception.ConflitoDeReservaException;
import com.projarc.trabalhofinal.domain.exception.PlanoInvalidoException;
import com.projarc.trabalhofinal.domain.exception.PlanoNaoEncontradoException;
import com.projarc.trabalhofinal.domain.exception.TransicaoInvalidaException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/planos/{id}")
@RequiredArgsConstructor
public class CicloDeVidaController {

    private final CicloDeVidaInPort cicloDeVidaInPort;

    @PostMapping("/validacao")
    public ResponseEntity<ValidacaoResponse> validar(@PathVariable Integer id) {
        return ResponseEntity.ok(CicloDeVidaRestMapper.toValidacao(cicloDeVidaInPort.validar(id)));
    }

    @PostMapping("/aprovacao")
    public ResponseEntity<SituacaoDoPlanoResponse> aprovar(@PathVariable Integer id) {
        return ResponseEntity.ok(CicloDeVidaRestMapper.toSituacao(cicloDeVidaInPort.aprovar(id)));
    }

    @PostMapping("/abandono")
    public ResponseEntity<SituacaoDoPlanoResponse> abandonar(@PathVariable Integer id) {
        return ResponseEntity.ok(CicloDeVidaRestMapper.toSituacao(cicloDeVidaInPort.abandonar(id)));
    }

    @PostMapping("/cancelamento")
    public ResponseEntity<SituacaoDoPlanoResponse> cancelar(@PathVariable Integer id) {
        return ResponseEntity.ok(CicloDeVidaRestMapper.toSituacao(cicloDeVidaInPort.cancelar(id)));
    }

    @ExceptionHandler(PlanoNaoEncontradoException.class)
    public ResponseEntity<Void> planoNaoEncontrado() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler({TransicaoInvalidaException.class, ConflitoDeReservaException.class})
    public ResponseEntity<Void> conflito() {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(PlanoInvalidoException.class)
    public ResponseEntity<ValidacaoResponse> planoInvalido(PlanoInvalidoException e) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(CicloDeVidaRestMapper.toValidacao(e.getProblemas()));
    }
}
