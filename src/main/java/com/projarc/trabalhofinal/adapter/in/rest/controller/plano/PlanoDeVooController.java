package com.projarc.trabalhofinal.adapter.in.rest.controller.plano;

import com.projarc.trabalhofinal.application.port.in.plano.PlanoDeVooInPort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/planoDeVoo")
@RequiredArgsConstructor

public class PlanoDeVooController {

    private final PlanoDeVooInPort planoDeVoo;



}
